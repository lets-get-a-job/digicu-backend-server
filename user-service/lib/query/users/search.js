const { query } = require('../index');

/**
 * 회원 가입 테이블 정보
 * @typedef {{
 * email: string
 * hash_string: string
 * registration_Date: string
 * type: string
 * letter_ok: boolean
 * }} RegistratonInformation
 */

/**
 * 업체 가입 정보
 * @typedef {{
 * companyNumber: string
 * companyName: string
 * companyPhone: string
 * companyAddress: string
 * companyOwner: string
 * companyHomepage: string
 * }} CompanyInformation
 */

/**
 * 회원 등록번호 조회
 * @param {string} email
 * @param {boolean?} withPassword password도 같이 조회할지
 * @returns {Promise<RegistratonInformation | null>}
 */
async function findRegistrationByEmail(email, withPassword) {
  try {
    const responses = await query([
      {
        sql: `SELECT email, ${
          withPassword ? 'hash_string,' : ''
        } registration_date, type, letter_ok FROM registration WHERE email = ?`,
        values: [email],
      },
    ]);
    const rows = responses[0].rows;
    if (rows.length > 0) {
      return rows[0];
    } else {
      return null;
    }
  } catch (error) {
    throw error;
  }
}

/**
 * 이메일로 회사 조회. 성공하면 회사정보 리턴, 실패하면 null 리턴
 * @param {string} email
 * @returns {Promise<CompanyInformation | null>}
 */
async function findCompanyByEmail(email) {
  try {
    const response = await query([
      {
        sql: 'SELECT * FROM company_profile WHERE email = ?',
        values: [email],
      },
    ]);
    const rows = response[0].rows;
    if (rows.length > 0) {
      return rows[0];
    } else {
      return null;
    }
  } catch (error) {
    throw error;
  }
}

/**
 * 사업자번호로 회사 조회. 성공하면 회사정보 리턴, 실패하면 null 리턴
 * @param {string} companyNumber
 * @returns {Promise<CompanyInformation | null>}
 */
async function findCompanyByCompanyNumber(companyNumber) {
  try {
    const response = await query([
      {
        sql: 'SELECT * FROM company_profile WHERE company_number = ?',
        values: [companyNumber],
      },
    ]);
    const rows = response[0].rows;
    if (rows.length > 0) {
      return rows[0];
    } else {
      return null;
    }
  } catch (error) {
    throw error;
  }
}

module.exports = {
  findRegistrationByEmail,
  findCompanyByEmail,
  findCompanyByCompanyNumber,
};
