const { query } = require('../index');
const isEmail = require('validator/lib/isEmail');
const isURL = require('validator/lib/isURL');

function isSocialFormValid(email, profile_image, thumbnail_image, letter_ok) {
  return isEmail(email) && isURL(profile_image) && isURL(thumbnail_image);
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
        socialInfo.email,
        socialInfo.profile_image,
        socialInfo.thumbnail_image,
      )
    ) {
      return false;
    }
    const responses = await query([
      {
        sql:
          'UPDATE social_profile SET token = ?, email = ?, nickname = ?, profile_image = ?, thumbnail_image = ? WHERE social_id = ?',
        values: [
          socialInfo.token,
          socialInfo.email,
          socialInfo.nickname,
          socialInfo.profile_image,
          socialInfo.thumbnail_image
            ? socialInfo.thumbnail_image
            : socialInfo.profile_image,
          socialInfo.social_id,
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

module.exports = { updateSocial };
