import { query } from '../index';
import { MenuRegistartion, MenuPatch } from './MenuSchema';
import { ResultSetHeader } from 'mysql2/promise';
import isDate from 'validator/lib/isDate';

function menuRegistartionValidation(menu: MenuRegistartion) {
  const condition =
    Object.values(menu).every(v => v) &&
    menu.company_number &&
    menu.company_number.length === 10 &&
    menu.menu_value >= 0 &&
    menu.regi_date &&
    isDate(menu.regi_date);
  return condition;
}

function menuPatchValidation(menu: MenuPatch) {
  return menu.menu_id;
}

export async function registerMenu(menu: MenuRegistartion) {
  if (!menuRegistartionValidation(menu)) throw Error('wrong parameter');

  const responses = await query([
    {
      sql:
        'INSERT INTO menu (company_number, menu_name, menu_value, stock, regi_date) VALUES (?, ?, ?, ?, ?)',
      values: [
        menu.company_number,
        menu.menu_name,
        menu.menu_value,
        menu.stock,
        new Date(menu.regi_date),
      ],
    },
  ]);

  if ((responses[0].rows as ResultSetHeader).affectedRows > 0) {
    return;
  } else {
    throw Error('no insertion');
  }
}

export async function patchMenu(menu: MenuPatch) {
  if (!menuPatchValidation(menu)) throw Error('wrong parameter');

  let sql = 'UPDATE menu SET ';
  let targets = [];
  const values = [];
  if (menu.menu_name) {
    targets.push('menu_name=?');
    values.push(menu.menu_name);
  }
  if (menu.menu_value >= 0) {
    targets.push('menu_value=?');
    values.push(menu.menu_value);
  }
  if (menu.stock) {
    targets.push('stock=?');
    values.push(menu.stock);
  }

  if (targets.length > 0) {
    sql += targets.join(',') + ' WHERE menu_id=?';
    console.log(sql);
    values.push(menu.menu_id);
    const responses = await query([
      {
        sql,
        values,
      },
    ]);

    if ((responses[0].rows as ResultSetHeader).affectedRows > 0) {
      return;
    } else {
      throw Error('no action');
    }
  } else {
    throw Error('no action');
  }
}

export async function deleteMenu(menu_id: number) {
  if (!menu_id) throw Error('wrong parameter');

  const responses = await query([
    {
      sql: 'DELETE FROM menu WHERE menu_id=?',
      values: [menu_id],
    },
  ]);

  if ((responses[0].rows as ResultSetHeader).affectedRows > 0) {
    return;
  } else {
    throw Error('no action');
  }
}
