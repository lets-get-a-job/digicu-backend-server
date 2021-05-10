import { SearchSchema } from '../index';

export interface MenuRegistartion {
  company_number: string;
  menu_name: string;
  menu_value: number;
  stock: number;
  regi_date: Date;
}

export interface MenuFull extends MenuRegistartion {
  menu_id: number;
}

export interface MenuPatch
  extends Omit<MenuFull, 'company_number' | 'regi_date'> {}

export type MenuOrderBy =
  | 'company_number'
  | 'menu_name'
  | 'menu_value'
  | 'stock'
  | 'regi_date';

export interface MenuSearch extends SearchSchema {
  orderby?: MenuOrderBy;
}
