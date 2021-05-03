const { query } = require('../index');
const isEmail = require('validator/lib/isEmail');
const isURL = require('validator/lib/isURL');
const isNumeric = require('validator/lib/isNumeric');

function isSocialFormValid(social_id, token, email, profile_image, thumbnail_image) {
  return (
    social_id && 
    token &&
    (!email || isEmail(email)) &&
    (!profile_image || isURL(profile_image, { require_tld: process.env.NODE_ENV === 'production' })) &&
    (!thumbnail_image || isURL(thumbnail_image, { require_tld: process.env.NODE_ENV === 'production' }))
  );
}

function isCompanyFormValid(
  companyNumber,
  companyPhone,
  companyHomepage,
  companyLogo,
) {
  return (
    companyNumber.length === 10 &&
    isNumeric(companyPhone) &&
    (companyPhone.length === 11 ||
      companyPhone.length === 10 ||
      companyPhone.length === 8) &&
    (!companyHomepage || isURL(companyHomepage, { require_tld: false })) &&
    (!companyLogo || isURL(companyLogo, { require_tld: false }))
  );
}

/**
 * 소셜 가입 정보
 * @typedef {{
 * social_id: string
 * token: string
 * email: string
 * nickname: string
 * profile_image: string
 * thumbnail_image: string
 * }} SocialInformation
 */

/**
 * 소셜 아이디로 소셜 프로필 업데이트
 * @param {SocialInformation} socialInfo
 * @returns {Promise<boolean>} 업데이트 여부
 */
async function updateSocial(socialInfo) {
  try {
    // form validation
    if (
      !isSocialFormValid(
        socialInfo.social_id,
        socialInfo.token,
        socialInfo.email,
        socialInfo.profile_image,
        socialInfo.thumbnail_image,
      )
    ) {
      return false;
    }
    let sql = 'UPDATE social_profile SET token = ?';
    const values = [socialInfo.token];
    if (socialInfo.email) {
      sql += ', email = ?';
      values.push(socialInfo.email);
    }
    if (socialInfo.nickname) {
      sql += ', nickname = ?';
      values.push(socialInfo.nickname);
    }
    if (socialInfo.profile_image) {
      sql += ', profile_image = ?';
      values.push(socialInfo.profile_image);
    }
    if (socialInfo.thumbnail_image) {
      sql += ', thumbnail_image = ?';
      values.push(socialInfo.thumbnail_image);
    }
    sql += ' WHERE social_id = ?';
    values.push(socialInfo.social_id);
    const responses = await query([
      {
        sql,
        values
      },
    ]);
    if (responses[0].rows.affectedRows > 0) {
      return true;
    } else {
      return false;
    }
  } catch (e) {
    throw e;
  }
}

/**
 * 업체 가입 정보
 * @typedef {{
 * company_number: string
 * company_name: string
 * company_phone: string
 * company_address: string
 * company_owner: string
 * company_homepage: string
 * company_logo: string
 * }} CompanyInformation
 */

/**
 * 사업자 등록번호로 소셜 프로필 업데이트
 * @param {CompanyInformation} comapnyInfo
 * @returns {Promise<boolean>} 업데이트 여부
 */
async function updateCompany(companyInfo) {
  try {
    if (
      !isCompanyFormValid(
        companyInfo.company_number,
        companyInfo.company_phone,
        companyInfo.company_homepage,
        companyInfo.company_logo,
      )
    ) {
      return false;
    }
    const responses = await query([
      {
        sql:
          'UPDATE company_profile SET company_name = ?, company_phone = ?, company_address = ?, company_owner = ?, company_homepage = ?, company_logo = ? WHERE company_number = ?',
        values: [
          companyInfo.company_name,
          companyInfo.company_phone,
          companyInfo.company_address,
          companyInfo.company_owner,
          companyInfo.company_homepage ? companyInfo.company_homepage : null,
          companyInfo.company_logo ? companyInfo.company_logo : null,
          companyInfo.company_number,
        ],
      },
    ]);
    if (responses[0].rows.affectedRows > 0) {
      return true;
    } else {
      return false;
    }
  } catch (e) {
    throw e;
  }
}

module.exports = { updateSocial, updateCompany };
