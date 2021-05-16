const { query } = require('../index')

/**
 * 회원 가입 테이블 정보
 * @typedef {{
 * email: string
 * hash_string: string
 * registration_Date: string
 * type: string
 * letter_ok: Date
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
 * company_logo: string | null
 * }} CompanyInformation
 */

/**
 * 소셜 가입 정보
 * @typedef {{
 * email: string
 * token: string
 * nickname: string
 * profile_image: string
 * thumbnail_image: string
 * letter_ok: Date
 * }} SocialInformation
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
    ])
    const rows = responses[0].rows
    if (rows.length > 0) {
      return rows[0]
    } else {
      return null
    }
  } catch (error) {
    throw error
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
    ])
    const rows = response[0].rows
    if (rows.length > 0) {
      return rows[0]
    } else {
      return null
    }
  } catch (error) {
    throw error
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
    ])
    const rows = response[0].rows
    if (rows.length > 0) {
      return rows[0]
    } else {
      return null
    }
  } catch (error) {
    throw error
  }
}

/**
 * 소셜 아이디로 소셜 프로필 조회
 * @param {string} socialID
 * @returns {Promise<SocialInformation | null>}
 */
async function findSocialBySocialID(socialID) {
  try {
    const response = await query([
      {
        sql: 'SELECT token, email, nickname, profile_image, thumbnail_image, registration_date, letter_ok, phone FROM social_profile WHERE social_id = ?',
        values: [socialID],
      },
    ])
    const rows = response[0].rows
    if (rows.length > 0) {
      rows[0].registration_date = new Date(rows[0].registration_date)
        .toLocaleDateString('ko-KR')
        .replace(/\./g, '')
        .replace(/ /g, '-')

      if (rows[0].letter_ok) {
        rows[0].letter_ok = new Date(rows[0].letter_ok)
          .toLocaleDateString('ko-KR')
          .replace(/\./g, '')
          .replace(/ /g, '-')
      }

      return rows[0]
    } else {
      return null
    }
  } catch (e) {
    throw e
  }
}

/**
 * 소셜 아이디로 fcm_token 조회 (로그인 상태에서만 가능)
 * @param {string} socialID
 * @returns {Promise<SocialInformation | null>}
 */
async function findSocialFCMTokenBySocialID(socialID) {
  try {
    const response = await query([
      {
        sql: 'SELECT fcm_token FROM fcm_token WHERE social_id = ?',
        values: [socialID],
      },
    ])
    const rows = response[0].rows
    if (rows.length > 0) {
      return rows[0]
    } else {
      return null
    }
  } catch (e) {
    throw e
  }
}

/**
 * email로 fcm_token 조회 (로그인 상태에서만 가능)
 * @query {string} email
 * @returns {Promise<SocialInformation | null>}
 */
async function findSocialFCMTokenByEmail(email) {
  try {
    const response = await query([
      {
        sql: 'SELECT fcm_token FROM fcm_token WHERE email = ?',
        values: [email],
      },
    ])
    const rows = response[0].rows
    if (rows.length > 0) {
      return rows[0]
    } else {
      return null
    }
  } catch (e) {
    throw e
  }
}

/**
 * 통합 검색
 * @param {string} include?
 * @param {number} count?
 * @param {number} page?
 * @param {string} orderby?
 * @param {boolean} desc?
 * @returns {Promise<CompanyInformation[] | null>}
 */
async function findCompany(include, count, page, orderby, desc) {
  try {
    let sql = 'SELECT * FROM company_profile'
    let where = ''
    let order = ''
    let limit = ''
    let offset = ''
    if (include) {
      where = where.concat(` company_name LIKE '%${include}%'`)
      where = where.concat(`OR company_address LIKE '%${include}%'`)
      where = where.concat(`OR company_owner LIKE '%${include}%'`)
    }
    if (orderby) {
      order = order.concat(`${orderby}`)
      if (desc) {
        order = order.concat(` DESC`)
      } else {
        order = order.concat(` ASC`)
      }
    }
    if (count) {
      limit = limit.concat(`${count}`)
    }
    if (page) {
      offset = offset.concat(`${(page - 1) * count}`)
    }
    if (where) {
      sql = `${sql} WHERE ${where}`
    }
    if (order) {
      sql = `${sql} ORDER BY ${order}`
    }
    if (limit) {
      sql = `${sql} LIMIT ${limit}`
    }
    if (offset) {
      sql = `${sql} OFFSET ${offset}`
    }
    const response = await query([
      {
        sql,
        values: [],
      },
    ])
    const rows = response[0].rows
    console.log(rows)
    if (rows.length > 0) {
      return rows
    } else {
      return null
    }
  } catch (error) {
    throw error
  }
}

module.exports = {
  findCompany,
  findRegistrationByEmail,
  findCompanyByEmail,
  findCompanyByCompanyNumber,
  findSocialBySocialID,
  findSocialFCMTokenBySocialID,
  findSocialFCMTokenByEmail,
}
