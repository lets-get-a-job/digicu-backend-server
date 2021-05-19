import { SearchSchema } from '../index'

export interface MenuRegistartion {
  company_number: string
  menu_name: string
  menu_value: number | string
  regi_date: string
}

export interface MenuFull extends MenuRegistartion {
  menu_id: string
}

export interface MenuPatch
  extends Omit<MenuFull, 'company_number' | 'regi_date'> {}

export type MenuOrderBy =
  | 'menu_id'
  | 'company_number'
  | 'menu_name'
  | 'menu_value'
  | 'regi_date'

export interface MenuSearch extends SearchSchema {
  orderby?: MenuOrderBy
}
