import mysql from 'mysql2/promise';

export interface SQLAction {
  sql: string;
  values: any[];
}

export interface SQLResponse {
  rows:
    | mysql.RowDataPacket[][]
    | mysql.RowDataPacket[]
    | mysql.OkPacket
    | mysql.OkPacket[]
    | mysql.ResultSetHeader;
  fields: mysql.FieldPacket[];
}

const pool = mysql.createPool({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASS,
  database: process.env.DB_NAME,
});

export async function query(sqlActions: SQLAction[]) {
  let connection = null;
  const sqlResponse: SQLResponse[] = [];
  try {
    connection = await pool.getConnection();
    await connection.beginTransaction();
    for (let action of sqlActions) {
      const [rows, fields] = await connection.query(action.sql, action.values);
      sqlResponse.push({ rows, fields });
    }
    await connection.commit();
    connection.release();
    return sqlResponse;
  } catch (error) {
    if (connection) {
      await connection.rollback(); // 요청한 쿼리 중 하나라도 실패하면 rollback
      connection.release();
    }
    throw error;
  }
}

export interface SearchSchema {
  include?: string;
  count?: number;
  page?: number;
  orderby?: string;
  desc?: boolean;
}
