const express = require('express');
const router = express.Router();

// NOTE 임시 코드이며 SQL쪽은 나중에 모듈화 할 것입니다.
const mysql = require('mysql2/promise');

const pool = mysql.createPool({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASS,
  database: process.env.DB_NAME,
});

router.get('/', async (req, res, next) => {
  const connection = await pool.getConnection();
  try {
    const [
      rows,
    ] = await connection.query('SELECT * FROM test_table where name = ?', [
      '유제환',
    ]);
    connection.release();
    res.send(rows);
  } catch (error) {
    connection.release();
    res.status(500);
    if (process.env.NODE_ENV === 'development') {
      res.send(`error message: ${error}`);
    }
  }
});

module.exports = router;
