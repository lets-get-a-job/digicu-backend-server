import express from 'express'
import {
  registerMenu,
  patchMenu,
  deleteMenu,
  searchMenu,
  findAllMenuWithCompanyNumber,
} from '../lib/query/menu'
import {
  MenuOrderBy,
  MenuPatch,
  MenuRegistartion,
} from '../lib/query/menu/MenuSchema'
import { sendError } from '../lib/routing/error-handling'
const router = express.Router()

/** 메뉴 등록 */
router.post('/:company_number', async function (req, res) {
  try {
    const menu = {
      company_number: req.params.company_number,
    }
    Object.assign(menu, req.body)
    await registerMenu(menu as MenuRegistartion)
    res.send('등록 성공')
  } catch (error) {
    if (error.message === 'wrong parameter') {
      sendError(res, 400, '파라미터 오류', '파라미터 오류')
    } else if (error.message === 'no action') {
      sendError(res, 400, '삽입 안됨', '삽입 안됨')
    } else {
      sendError(res, 500, '에러 발생', error)
    }
  }
})

/** 메뉴 수정 */
router.patch('/:menu_id', async function (req, res) {
  try {
    const menu = {
      menu_id: Number(req.params.menu_id),
    }
    Object.assign(menu, req.body)
    await patchMenu(menu as MenuPatch)
    res.send('수정 성공')
  } catch (error) {
    if (error.message === 'wrong parameter') {
      sendError(res, 400, '파라미터 오류', '파라미터 오류')
    } else if (error.message === 'no action') {
      sendError(res, 400, '수정 안됨', '수정 안됨')
    } else {
      sendError(res, 500, '에러 발생', error)
    }
  }
})

/** 메뉴 삭제 */
router.delete('/:menu_id', async function (req, res) {
  try {
    await deleteMenu(Number(req.params.menu_id))
    res.send('삭제 성공')
  } catch (error) {
    if (error.message === 'wrong parameter') {
      sendError(res, 400, '파라미터 오류', '파라미터 오류')
    } else if (error.message === 'no action') {
      sendError(res, 400, '삭제 안됨', '삭제 안됨')
    } else {
      sendError(res, 500, '에러 발생', error)
    }
  }
})

/** 메뉴 조회 (통합 검색) */
router.get('/', async function (req, res) {
  try {
    const found = await searchMenu({
      include: (req.query.include as string) || undefined,
      orderby: (req.query.orderby as MenuOrderBy) || undefined,
      desc: req.query.desc === 'true',
      count: req.query.count ? Number(req.query.count) : 10,
      page: req.query.page ? Number(req.query.page) : 1,
    })
    res.json(found)
  } catch (error) {
    sendError(res, 500, '에러 발생', error)
  }
})

/** 메뉴 조회 (id) */
router.get('/:company_number', async function (req, res) {
  try {
    const found = await findAllMenuWithCompanyNumber(req.params.company_number)
    res.json(found)
  } catch (error) {
    if (error.message === 'wrong parameter') {
      sendError(res, 400, '파라미터 오류', '파라미터 오류')
    } else {
      sendError(res, 500, '에러 발생', error)
    }
  }
})

export default router
