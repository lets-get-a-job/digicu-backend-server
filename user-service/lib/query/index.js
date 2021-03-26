/**
 * SQLAction에 대한 Interface
 * @typedef {[{sql: string, values: any[]}]} SQLActions
 */

/**
 * SQLResponse에 대한 Interface
 * @typedef {{rows: mysql.RowDataPacket[][] | mysql.RowDataPacket[] | mysql.OkPacket | mysql.OkPacket[] | mysql.ResultSetHeader, fields: mysql.FieldPacket[]}} SQLResponse
 */

const mysql = require('mysql2/promise');

/**
 * SQL 풀 오브젝트
 * @private
 * @type { mysql.Pool }
 */
const pool = mysql.createPool({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASS,
  database: process.env.DB_NAME,
});

/**
 * 기본적인 query 함수. 트랜잭션 적용 (sqlActions에 있는 쿼리가 모두 성공해야함)
 * @param {SQLActions} sqlActions
 * @param {any[]} values
 * @returns { Promise<SQLResponse[]> } { rows, fields }
 */
async function query(sqlActions) {
  let connection = null;
  const sqlResponse = [];
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
    await connection?.rollback(); // 요청한 쿼리 중 하나라도 실패하면 rollback
    connection?.release();
    throw error;
  }
}

module.exports = {
  query,
};
