/**
 * Profile에 대한 인터페이스
 * @typedef {{email: string, nick: string, image_url: string}} Profile
 */

const { query } = require('./query');
const bcrypt = require('bcrypt');
const isEmail = require('validator/lib/isEmail');

/**
 * 회원을 등록하는 함수
 * @param {string} email
 * @param {string} plainPassword
 * @param {string} nick
 * @param {boolean} letterOK
 * @returns {Promise<boolean>} 등록 성공 여부
 */
async function registerUser(email, plainPassword, nick, letterOK) {
  const validation = isEmail(email) && typeof letterOK === 'boolean';
  const saltRounds = 10;

  if (validation) {
    try {
      // 평문 패스워드를 해시값으로 바꿉니다.
      const hash_string = await bcrypt.hash(plainPassword, saltRounds);
      // 유저 정보를 등록합니다.
      await query([
        {
          sql: 'INSERT INTO registration VALUES (?, ?, ?, ?)',
          values: [email, hash_string, null, letterOK ? new Date() : null],
        },
        {
          sql: 'INSERT INTO profile VALUES (?, ?, ?)',
          values: [email, nick, null],
        },
      ]);
      return true;
    } catch (error) {
      throw error;
    }
  } else {
    // validation 실패
    return false;
  }
}

/**
 * 이메일로 유저 삭제
 * @param {string} email
 * @returns {Promise<boolean>} 성공 여부
 */
async function removeUser(email) {
  if (isEmail(email)) {
    // registrtion과 profile이 email 속성으로 cascade 참조 되어 있음.
    const response = await query([
      {
        sql: 'DELETE FROM registration WHERE email = ?',
        values: [email],
      },
    ]);
    /**
     * @var { mysql.ResultSetHeader }
     */
    const resultSetHeader = response[0].rows;
    if (resultSetHeader.affectedRows > 0) {
      return true;
    } else {
      return false;
    }
  } else {
    return false;
  }
}

/**
 * 이메일로 유저 조회. 성공하면 Profile 리턴, 실패하면 false 리턴
 * @param {string} email
 * @returns {Promise<Profile | false>}
 */
async function findUserByEmail(email) {
  if (isEmail(email)) {
    try {
      const response = await query([
        {
          sql: 'SELECT * FROM profile WHERE email = ?',
          values: [email],
        },
      ]);
      const rows = response[0].rows;
      if (rows.length > 0) {
        return rows[0];
      } else {
        return false;
      }
    } catch (error) {
      throw error;
    }
  } else {
    return false;
  }
}

/**
 * 닉네임으로 유저 조회. 성공하면 Profile 리턴, 실패하면 false 리턴
 * @param {string} nick
 * @returns {Promise<Profile | false>}
 */
async function findUserByNick(nick) {
  try {
    const response = await query([
      {
        sql: 'SELECT * FROM profile WHERE nick = ?',
        values: [nick],
      },
    ]);
    const rows = response[0].rows;
    if (rows.length > 0) {
      return rows[0];
    } else {
      return false;
    }
  } catch (error) {
    throw error;
  }
}

module.exports = {
  registerUser,
  removeUser,
  findUserByEmail,
  findUserByNick,
};
