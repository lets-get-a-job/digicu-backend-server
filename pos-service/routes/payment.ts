import express from 'express'
import { registerPayment, searchPayment } from '../lib/query/payment'
import {
  PaymentOrderBy,
  PaymentRequest,
} from '../lib/query/payment/PaymentSchema'
import { sendError } from '../lib/routing/error-handling'
const router = express.Router()

/** 결제 API */
router.post('/:company_number', async (req, res) => {
  try {
    const payment = req.body

    Object.assign(payment.payment_info, {
      company_number: req.params.company_number,
    })

    await registerPayment(payment as PaymentRequest)
    res.send('등록 성공')
  } catch (error) {
    if (error.message === 'wrong parameter') {
      sendError(res, 400, '파라미터 오류', '파라미터 오류')
    } else if (error.message === 'no action') {
      sendError(res, 400, '삽입 안됨', '삽입 안됨')
    } else if (error.errno === 3140) {
      sendError(res, 400, 'coupon json 형식 오류', 'coupon json 형식 오류')
    } else if (error.errno === 1452) {
      sendError(res, 400, '없는 메뉴 입니다.', '없는 메뉴 입니다.')
    } else {
      sendError(res, 500, '에러 발생', error)
    }
  }
})

/** 결제내역 API */
router.get('/:company_number', async (req, res) => {
  try {
    const found = await searchPayment({
      include: (req.query.include as string) || undefined,
      orderby: (req.query.orderby as PaymentOrderBy) || 'payment_id',
      desc: req.query.desc === 'true',
      count: req.query.count ? Number(req.query.count) : 10,
      page: req.query.page ? Number(req.query.page) : 1,
      start: req.query.start ? new Date(req.query.start as string) : new Date(),
      end: req.query.end ? new Date(req.query.end as string) : null,
    })
    res.json(found)
  } catch (error) {
    if (error.errno === 1064) {
      sendError(res, 400, '파라미터 오류', error)
    } else {
      sendError(res, 500, '에러 발생', error)
    }
  }
})

export default router
