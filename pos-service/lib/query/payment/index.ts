import { query } from '../index'
import {
  PaymentFull,
  PaymentRegistration,
  PaymentSearch,
} from './PaymentSchema'
import { ResultSetHeader, RowDataPacket } from 'mysql2/promise'
import moment from 'moment'

const isDateTime = (datetime: string) =>
  new RegExp(/\d{4}-[01]\d-[0-3]\d [0-2]\d:[0-5]\d:[0-5]\d/g).test(datetime)

function paymentRegistrationValidation(payment: PaymentRegistration) {
  const condition =
    Object.values(payment).every(v => v) &&
    payment.payment_value >= 0 &&
    isDateTime(payment.payment_time) &&
    Array.isArray(JSON.parse(payment.coupons as any))
  return condition
}

export async function registerPayment(payment: PaymentRegistration) {
  if (!paymentRegistrationValidation(payment)) throw Error('wrong parameter')

  const responses = await query([
    {
      sql: 'INSERT INTO payment (company_number, menu_id, payment_value, payment_time, payer_id, coupons) VALUES (?, ?, ?, ?, ?, ?)',
      values: [
        payment.company_number,
        payment.menu_id,
        payment.payment_value,
        payment.payment_time,
        payment.payer_id,
        payment.coupons,
      ],
    },
  ])

  if ((responses[0].rows as ResultSetHeader).affectedRows > 0) {
    return
  } else {
    throw Error('no insertion')
  }
}

export async function searchPayment(searchQuery: PaymentSearch) {
  let sql = 'SELECT * FROM payment'
  let where = ''
  let order = ''
  let limit = ''
  let offset = ''
  if (searchQuery.start) {
    where = where.concat(
      `payment_time >= ${moment(searchQuery.start).format('YYYYMMDD')}`,
    )
    if (searchQuery.end) {
      where = where.concat(
        ` AND payment_time < ${moment(searchQuery.end).format('YYYYMMDD')}`,
      )
    } else {
      searchQuery.end = new Date()
      where = where.concat(
        ` AND payment_time < DATE_ADD(${moment(searchQuery.end).format(
          'YYYYMMDD',
        )}, INTERVAL +1 DAY)`,
      )
    }
  }
  if (searchQuery.orderby) {
    order = order.concat(`${searchQuery.orderby}`)
    if (searchQuery.desc) {
      order = order.concat(` DESC`)
    } else {
      order = order.concat(` ASC`)
    }
  }
  if (searchQuery.count) {
    limit = limit.concat(`${searchQuery.count}`)
  }
  if (searchQuery.page) {
    offset = offset.concat(`${(searchQuery.page - 1) * searchQuery.count}`)
  }
  if (where) {
    sql = `${sql} WHERE ${where}`
  }
  if (order) {
    sql = `${sql} ORDER BY ${order}`
  }
  if (limit) {
    sql = `${sql} LIMIT ${limit}`
  }
  if (offset) {
    sql = `${sql} OFFSET ${offset}`
  }
  console.log(sql)
  const responses = await query([
    {
      sql,
      values: [],
    },
  ])

  if ((responses[0].rows as RowDataPacket[]).length > 0) {
    return (responses[0].rows as RowDataPacket[]).map(v => {
      v.payment_time = moment(v.payment_time).format('YYYY-MM-DD HH:mm:ss')
      return v
    })
  } else {
    return []
  }
}
