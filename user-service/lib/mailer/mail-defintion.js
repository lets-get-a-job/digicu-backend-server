const { sendMail } = require('./index');

/**
 *
 * @param {string} to mail address
 * @param {string} from mail address
 * @param {string} token
 */
function sendAuthEmail(to, from, token) {
  sendMail({
    to: to,
    from: from,
    subject: '[디지쿠] 가입 인증 메일',
    html: `
        <h1>가입을 진심으로 환영합니다.</h1>
        <p><a href="${process.env.SMTP_ORIGIN}/users/${to}/auth?type=reg&token=${token}">여기</a>에 접속하여 인증을 해주세요.</p>
        <p>Digicu 2021</p>
    `,
  });
}

module.exports = {
  sendAuthEmail,
};
