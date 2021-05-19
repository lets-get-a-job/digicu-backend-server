import { SearchSchema } from '../index'

export interface PaymentRequest {
  payment_info: {
    company_number: string
    sale: number
    sum: number
    total: number
    payment_time: string
  }
  payment_items: Array<{
    menu_id: number
    payment_count: number
    payment_value: number
  }>
}

export interface PaymentResponse {
  payment_id: number
  payment_group_id: string
  company_number: string
  menu_id: number
  payment_value: number
  payment_count: number
  payment_time: string
}

export type PaymentOrderBy =
  | 'payment_id'
  | 'company_number'
  | 'menu_id'
  | 'payment_value'
  | 'payment_count'
  | 'payment_time'
  | 'payment_group_id'
  | 'sale'
  | 'sum'
  | 'total'

export interface PaymentSearch extends SearchSchema {
  orderby?: PaymentOrderBy
  start?: Date
  end?: Date
}
