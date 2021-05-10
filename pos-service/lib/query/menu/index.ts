import { query } from '../index';
import { MenuRegistartion, MenuPatch, MenuSearch } from './MenuSchema';
import { ResultSetHeader, RowDataPacket } from 'mysql2/promise';
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

export async function findMenuWithID(menu_id: number) {
  if (!menu_id) throw Error('wrong parameter');

  const responses = await query([
    {
      sql: 'SELECT * FROM menu WHERE menu_id=?',
      values: [menu_id],
    },
  ]);

  const found = responses[0].rows[0];

  if (found) {
    found.regi_date = new Date(found.regi_date)
      .toLocaleDateString('ko-kr')
      .replace(/\. /g, '-')
      .replace(/\./, '');
    return found;
  } else {
    return null;
  }
}

export async function searchMenu(searchQuery: MenuSearch) {
  let sql = 'SELECT * FROM menu';
  let where = '';
  let order = '';
  let limit = '';
  let offset = '';
  if (searchQuery.include) {
    where = where.concat(` menu_name LIKE '%${searchQuery.include}%'`);
  }
  if (searchQuery.orderby) {
    order = order.concat(`${searchQuery.orderby}`);
    if (searchQuery.desc) {
      order = order.concat(` DESC`);
    } else {
      order = order.concat(` ASC`);
    }
  }
  if (searchQuery.count) {
    limit = limit.concat(`${searchQuery.count}`);
  }
  if (searchQuery.page) {
    offset = offset.concat(`${(searchQuery.page - 1) * searchQuery.count}`);
  }
  if (where) {
    sql = `${sql} WHERE ${where}`;
  }
  if (order) {
    sql = `${sql} ORDER BY ${order}`;
  }
  if (limit) {
    sql = `${sql} LIMIT ${limit}`;
  }
  if (offset) {
    sql = `${sql} OFFSET ${offset}`;
  }
  console.log(sql);
  const responses = await query([
    {
      sql,
      values: [],
    },
  ]);

  if ((responses[0].rows as RowDataPacket[]).length > 0) {
    return (responses[0].rows as RowDataPacket[]).map(v => {
      v.regi_date = new Date(v.regi_date)
        .toLocaleDateString('ko-kr')
        .replace(/\. /g, '-')
        .replace(/\./, '');
      return v;
    });
  } else {
    return [];
  }

  return;
}
