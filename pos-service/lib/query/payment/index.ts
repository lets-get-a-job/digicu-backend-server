import { query } from '../index'
import { PaymentResponse, PaymentRequest, PaymentSearch } from './PaymentSchema'
import { ResultSetHeader, RowDataPacket } from 'mysql2/promise'
import moment from 'moment'
import randomstring from 'randomstring'
import _ from 'underscore'

const isDateTime = (datetime: string) =>
  new RegExp(/\d{4}-[01]\d-[0-3]\d [0-2]\d:[0-5]\d:[0-5]\d/g).test(datetime)

function paymentRegistrationValidation(payment: PaymentRequest) {
  if (payment.payment_info && payment.payment_items.length > 0) {
    const { company_number, payment_time, sale, sum, total } =
      payment.payment_info

    const condtion1 =
      company_number &&
      isDateTime(payment_time) &&
      sale >= 0 &&
      sum >= 0 &&
      total === sum - sale

    const condtion2 = payment.payment_items.every(
      ({ menu_id, payment_count, payment_value }) =>
        menu_id && payment_count > 0 && payment_value > 0,
    )

    return condtion1 && condtion2
  } else {
    return false
  }
}

export async function registerPayment(payment: PaymentRequest) {
  if (!paymentRegistrationValidation(payment)) throw Error('wrong parameter')

  const { company_number, sale, sum, total, payment_time } =
    payment.payment_info

  const payment_group_id = randomstring.generate(10) + Date.now()

  const responses = await query([
    {
      sql: 'INSERT INTO payment_group (payment_group_id, company_number, sale, sum, total, payment_time) VALUES (?, ?, ?, ?, ?, ?)',
      values: [
        payment_group_id,
        company_number,
        sale,
        sum,
        total,
        payment_time,
      ],
    },
  ])

  if ((responses[0].rows as ResultSetHeader).affectedRows > 0) {
    // 삽입 쿼리 생성
    const queryList = payment.payment_items.map(item => {
      const { menu_id, payment_count, payment_value } = item
      return {
        sql: 'INSERT INTO payment_items (payment_group_id, menu_id, payment_value, payment_count) VALUES (?, ?, ?, ?)',
        values: [payment_group_id, menu_id, payment_count, payment_value],
      }
    })

    // 삽입 쿼리 실행
    const responses = await query(queryList)

    if ((responses[0].rows as ResultSetHeader).affectedRows <= 0) {
      throw Error('no insertion')
    }
    return
  } else {
    throw Error('no insertion')
  }
}

export async function searchPayment(searchQuery: PaymentSearch) {
  let sql =
    'SELECT * FROM payment_group NATURAL JOIN payment_items NATURAL JOIN menu'
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
    const result = (responses[0].rows as RowDataPacket[]).map(v => {
      v.payment_time = moment(v.payment_time).format('YYYY-MM-DD HH:mm:ss')
      return v
    })

    return _.chain(result)
      .groupBy('payment_group_id')
      .map(rows => {
        return {
          payment_group_id: rows[0].payment_group_id,
          sale: rows[0].sale,
          sum: rows[0].sum,
          total: rows[0].total,
          company_number: rows[0].company_number,
          payment_time: rows[0].payment_time,
          items: rows.map(v => ({
            payment_id: v.payment_id,
            menu_id: v.menu_id,
            menu_name: v.menu_name,
            payment_value: v.payment_value,
            payment_count: v.payment_count,
          })),
        }
      })
  } else {
    return []
  }
}
