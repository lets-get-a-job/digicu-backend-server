const { query } = require('../index');
const bcrypt = require('bcrypt');
const randomstring = require('randomstring');
const { sendAuthEmail } = require('../../mailer/mail-defintion');
const isEmail = require('validator/lib/isEmail');
const isURL = require('validator/lib/isURL');
const isNumeric = require('validator/lib/isNumeric');

function isCompanyFormValid(
  email,
  letterOK,
  companyNumber,
  companyPhone,
  companyHomepage,
  companyLogo,
) {
  return (
    isEmail(email) &&
    typeof letterOK === 'boolean' &&
    companyNumber.length === 10 &&
    isNumeric(companyPhone) &&
    (companyPhone.length === 11 ||
      companyPhone.length === 10 ||
      companyPhone.length === 8) &&
    isURL(companyHomepage) &&
    (!companyLogo || isURL(companyLogo))
  );
}

function isSocialFormValid(email, profile_image, thumbnail_image, letter_ok) {
  return (
    isEmail(email) &&
    isURL(profile_image) &&
    isURL(thumbnail_image) &&
    typeof letter_ok === 'boolean'
  );
}

/**
 * 기본 가입 정보
 * @typedef {{
 * email: string
 * plain_password: string
 * letter_ok: boolean
 * }} RegistratonInformation
 */

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
 * 소셜 가입 정보
 * @typedef {{
 * social_id: string
 * token: string
 * email: string
 * nickname: string
 * profile_image: string
 * thumbnail_image: string
 * letter_ok: boolean
 * }} SocialInformation
 */

/**
 *
 * @param {SocialInformation} socialInfo
 * @returns {Promise<boolean>} 회원가입 성공 여부
 */
async function registerSocial(socialInfo) {
  try {
    // form validation
    if (
      !isSocialFormValid(
        socialInfo.email,
        socialInfo.profile_image,
        socialInfo.thumbnail_image,
        socialInfo.letter_ok,
      )
    ) {
      return false;
    }
    const responses = await query([
      {
        sql: 'INSERT INTO social_profile VALUES (?, ?, ?, ?, ?, ?, ?, ?)',
        values: [
          socialInfo.social_id,
          socialInfo.token,
          socialInfo.email,
          socialInfo.nickname,
          socialInfo.profile_image,
          socialInfo.thumbnail_image
            ? socialInfo.thumbnail_image
            : socialInfo.profile_image,
          new Date(),
          socialInfo.letter_ok ? new Date() : null,
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

/**
 *
 * @async
 * @param {RegistratonInformation} regInfo
 * @param {CompanyInformation} companyInfo
 * @returns {Promise<boolean>} 회원가입 성공 여부
 */
async function registerCompany(regInfo, companyInfo) {
  try {
    // 입력된 폼이 유효한지 검사합니다.
    if (
      isCompanyFormValid(
        regInfo.email,
        regInfo.letter_ok,
        companyInfo.company_number,
        companyInfo.company_phone,
        companyInfo.company_homepage,
        companyInfo.company_logo,
      )
    ) {
      const saltRounds = 10;
      // 평문 패스워드를 해시값으로 바꿉니다.
      const hash_string = await bcrypt.hash(regInfo.plain_password, saltRounds);
      // 이메일 인증을 위한 토큰을 생성합니다.
      const token = randomstring.generate(30);

      // 회원 가입
      const responses = await query([
        // 업주 등록 쿼리
        {
          sql: 'INSERT INTO registration VALUES (?, ?, ?, ?, ?)',
          values: [
            regInfo.email,
            hash_string,
            null,
            'company',
            regInfo.letter_ok ? new Date() : null,
          ],
        },
        // 업주 프로필 생성 쿼리
        {
          sql: 'INSERT INTO company_profile VALUES (?, ?, ?, ?, ?, ?, ?, ?)',
          values: [
            regInfo.email,
            companyInfo.company_number,
            companyInfo.company_name,
            companyInfo.company_phone,
            companyInfo.company_address,
            companyInfo.company_owner,
            companyInfo.company_homepage,
            companyInfo.company_logo ? companyInfo.company_logo : null,
          ],
        },
        // 이메일 인증 토큰 생성 쿼리
        {
          sql: 'INSERT INTO authentication VALUES(?, ?, ?, ?)',
          values: [regInfo.email, token, 'reg', null], // 이메일 인증 토큰은 만료기간이 없습니다.
        },
      ]);
      if (responses[0].rows.affectedRows > 0) {
        // SMTP로 인증 메일을 보냅니다.
        await sendAuthEmail(
          regInfo.email,
          `"Digicu" <${process.env.SMTP_FROM}>`,
          token,
        );
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  } catch (error) {
    throw error;
  }
}

async function registerAdmin(regInfo) {}

module.exports = {
  registerCompany,
  registerSocial,
};
