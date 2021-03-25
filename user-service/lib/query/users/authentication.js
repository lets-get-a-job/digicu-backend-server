const { query } = require('../index');
const randomstring = require('randomstring');
const bcrypt = require('bcrypt');
const { errorHandling } = require('../../routing');

/**
 * 이메일 인증을 완료합니다.
 * @param {string} email
 * @param {"reg" | "pwd"} type
 * @param {string} token
 * @returns { Promise<boolean> }
 */
async function confirmEmailAuth(email, token) {
  try {
    const response = await query([
      {
        sql:
          'DELETE FROM authentication WHERE email = ? and type = ? and token = ?',
        values: [email, 'reg', token],
      },
    ]);
    /**
     * @var { mysql.ResultSetHeader }
     */
    const resultSetHeader = response[0].rows;
    if (resultSetHeader.affectedRows > 0) {
      // == 인증 정보가 존재한다.
      await query([
        {
          sql: 'UPDATE registration SET registration_date = ? WHERE email = ?',
          values: [new Date(), email],
        },
      ]); // 인증 날짜를 기록한다.
      return true;
    } else {
      return false;
    }
  } catch (error) {
    throw error;
  }
}

/**
 *
 * @param {string} email 사용자 이메일
 * @param {string} plainPassword 사용자 패스워드
 * @returns { Promise<string | null> } 토큰을 리턴합니다.
 */
async function createChangePasswordToken(email, plainPassword) {
  const response = await query([
    {
      sql: 'SELECT hash_string FROM registration WHERE email = ?',
      values: [email],
    },
  ]);
  if (response[0].rows.length > 0) {
    const user = response[0].rows[0];
    const token = randomstring.generate(30);
    if (await bcrypt.compare(plainPassword, user.hash_string)) {
      await query([
        {
          sql: 'INSERT INTO authentication VALUES(?, ?, ?, ?)',
          values: [email, token, 'pwd', new Date(Date.now() + 5 * 60000)], // 유효시간 5분
        },
      ]);
      return token;
    } else {
      return null;
    }
  } else {
    return null;
  }
}

/**
 * 비밀번호를 변경합니다.
 * @param {string} email 사용자 이메일
 * @param {string} plainPassword 변경할 패스워드
 * @param {string} token
 * @returns { Promise<boolean> }
 */
async function changePassword(email, plainPassword, token) {
  try {
    const saltRounds = 10;
    const hash_string = await bcrypt.hash(plainPassword, saltRounds);
    const response = await query([
      {
        sql:
          'DELETE FROM authentication WHERE email = ? and type = ? and token = ? and expiration >= ?',
        values: [email, 'pwd', token, new Date(Date.now())], // 유효기간 검사
      },
    ]);
    /**
     * @var { mysql.ResultSetHeader }
     */
    const resultSetHeader = response[0].rows;
    if (resultSetHeader.affectedRows > 0) {
      // == 인증 정보가 존재한다.
      await query([
        {
          sql: 'UPDATE registration SET hash_string = ? WHERE email = ?',
          values: [hash_string, email],
        },
      ]); // 패스워드를 변경한다.
      return true;
    } else {
      // 불필요한 인증정보가 있으면 삭제
      await query([
        {
          sql:
            'DELETE FROM authentication WHERE email = ? and type = ? and token = ?',
          values: [email, 'pwd', token],
        },
      ]);
      return false;
    }
  } catch (error) {
    throw error;
  }
}

/**
 * 이메일 인증이 완료된 계정인지 검사합니다.
 * @param {string} email
 * @returns {Promise<boolean>}
 */
async function isEmailChecked(email) {
  try {
    const responses = await query([
      {
        sql: 'SELECT registration_date FROM registration WHERE email = ?',
        values: [email],
      },
    ]);
    const rows = responses[0].rows;
    if (rows.length > 0) {
      const { registration_date } = rows[0];
      // 이메일 인증과 동시에 가입일이 기록되기 때문에 null이 아니면 인증이 된 것
      return registration_date !== null;
    } else {
      return false;
    }
  } catch (error) {
    throw error;
  }
}

module.exports = {
  confirmEmailAuth,
  changePassword,
  createChangePasswordToken,
  isEmailChecked,
};
