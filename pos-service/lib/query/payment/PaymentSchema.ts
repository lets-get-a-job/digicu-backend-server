import { SearchSchema } from '../index'

export interface PaymentRegistration {
  company_number: string
  menu_id: string
  payment_value: number | string
  payment_time: string
  payer_id: string
  coupons: Array<string>
}

export interface PaymentFull extends PaymentRegistration {
  payment_id: string
}

export type PaymentOrderBy =
  | 'payment_id'
  | 'company_number'
  | 'menu_id'
  | 'payment_value'
  | 'payment_time'
  | 'payer_id'

export interface PaymentSearch extends SearchSchema {
  orderby?: PaymentOrderBy
  start?: Date
  end?: Date
}
