const express = require('express');
const router = express.Router();

const { registraton, search, authentication } = require('../lib/query/users');
const { errorHandling } = require('../lib/routing');

/** 이메일로 유저 조회 */
router.get('/:email', async (req, res) => {
  try {
    const user = await search.findRegistrationByEmail(req.params.email);
    if (user) {
      user.registration_date = new Date(
        user.registration_date,
      ).toLocaleDateString('ko-KR');
      user.letter_ok = user.letter_ok
        ? new Date(user.letter_ok).toLocaleDateString('ko-KR')
        : null;
      if (user.type === 'company') {
        const companyProfile = await search.findCompanyByEmail(
          req.params.email,
        );
        const responseData = Object.assign(user, companyProfile);
        res.json(responseData);
      } else if (user.type === 'social') {
        // TODO 소셜 로그인 구현해야함
        res.status(404);
        res.send('미구현');
      }
    } else {
      res.status(404);
      res.json(null);
    }
  } catch (error) {
    errorHandling.sendError(res, 500, '오류가 발생했습니다.', error);
  }
});

/** 사업자등록번호로 회사 조회 */
router.get('/company/:companyNumber', async (req, res) => {
  try {
    const user = await search.findCompanyByCompanyNumber(
      req.params.companyNumber,
    );
    res.json(user);
  } catch (error) {
    errorHandling.sendError(res, 500, '오류가 발생했습니다.', error);
  }
});

/** 회사 등록 */
router.post('/company', async (req, res) => {
  try {
    const { reg_info, company_info } = req.body;
    const isRegisterd = await registraton.registerCompany(
      reg_info,
      company_info,
    );
    if (isRegisterd) {
      res.sendStatus(200);
    } else {
      res.sendStatus(400);
    }
  } catch (error) {
    errorHandling.sendError(res, 500, '오류가 발생했습니다.', error);
  }
});

/** 가입 인증이 되었는지 확인 */
router.get('/:email/checked', async (req, res) => {
  try {
    const isChecked = await authentication.isEmailChecked(
      'formail0001@naver.com',
    );
    res.json({
      is_checked: isChecked,
    });
  } catch (error) {
    errorHandling.sendError(res, 500, '에러가 발생했습니다.', error);
  }
});

module.exports = router;
