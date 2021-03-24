const { query } = require('../index');
const isEmail = require('validator/lib/isEmail');

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
 * 이메일로 회사 조회. 성공하면 회사정보 리턴, 실패하면 null 리턴
 * @param {string} email
 * @returns {Promise<CompanyInformation | null>}
 */
async function findCompanyByEmail(email) {
  if (isEmail(email)) {
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
  } else {
    return null;
  }
}

module.exports = {
  findCompanyByEmail,
};
