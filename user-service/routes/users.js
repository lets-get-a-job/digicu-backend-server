const express = require('express');
const router = express.Router();

const {
  registraton,
  search,
  authentication,
  update,
} = require('../lib/query/users');
const { errorHandling } = require('../lib/routing');
const { isLoggedIn } = require('../lib/middleware');
const jwt = require('jsonwebtoken');

/** 이메일로 유저 조회 */
router.get('/company/:email', async (req, res) => {
  try {
    const user = await search.findRegistrationByEmail(req.params.email);
    if (user) {
      user.registration_date = new Date(user.registration_date)
        .toLocaleDateString('ko-KR')
        .replace(/\./g, '-')
        .replace(/ /g, '-');
      user.letter_ok = user.letter_ok
        ? new Date(user.letter_ok)
            .toLocaleDateString('ko-KR')
            .replace(/\./g, '-')
            .replace(/ /g, '-')
        : null;
      if (user.type === 'company') {
        const companyProfile = await search.findCompanyByEmail(
          req.params.email,
        );
        const responseData = Object.assign(user, companyProfile);
        res.json(responseData);
      } else {
        res.status(404);
        res.json(null);
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
    if (error.code === 'ER_DUP_ENTRY') {
      errorHandling.sendError(
        res,
        400,
        '이미 존재하는 메일입니다.',
        '이미 존재하는 메일입니다.',
      );
    } else if (
      error.code === 'ER_BAD_NULL_ERROR' ||
      error instanceof TypeError
    ) {
      errorHandling.sendError(
        res,
        400,
        '파라미터가 잘못되었습니다.',
        '파라미터가 잘못되었습니다.',
      );
    } else {
      errorHandling.sendError(res, 500, '오류가 발생했습니다.', error);
    }
  }
});

/** 가입 인증이 되었는지 확인 */
router.get('/:email/checked', async (req, res) => {
  try {
    const isChecked = await authentication.isEmailChecked(req.params.email);
    res.json({
      is_checked: isChecked,
    });
  } catch (error) {
    errorHandling.sendError(res, 500, '에러가 발생했습니다.', error);
  }
});

/** 소셜 가입 정보 조회 */
router.get('/social/:social_id', async (req, res) => {
  try {
    const social = await search.findSocialBySocialID(req.params.social_id);
    if (social) {
      res.json(social);
    } else {
      res.status(404);
      res.json(null);
    }
  } catch (error) {
    errorHandling.sendError(res, 500, '에러가 발생했습니다.', error);
  }
});

/** 소설 가입 진행 */
router.post('/social', async (req, res) => {
  try {
    const body = req.body;
    const jwtSecret = process.env.JWT_SECRET;
    if (!body.thumbnail_image) {
      body.thumbnail_image = body.profile_image;
    }
    const isRegisterd = await registraton.registerSocial(body);
    if (isRegisterd) {
      jwt.sign(
        {
          email: body.email,
          socialInfo: body,
          admin: false,
          type: 'social',
        },
        jwtSecret,
        {
          expiresIn: '2h',
          issuer: 'digicu',
          subject: 'userLogin',
        },
        (err, token) => {
          if (err) {
            errorHandling.sendError(res, 500, '토큰 발급 실패', error);
          }
          res.send({
            digicu_token: token,
            expires_in: 7200000, // 2h
          });
        },
      );
    } else {
      res.sendStatus(400);
    }
  } catch (error) {
    if (error.code === 'ER_DUP_ENTRY') {
      errorHandling.sendError(
        res,
        400,
        '이미 존재하는 메일입니다.',
        '이미 존재하는 메일입니다.',
      );
    } else if (
      error.code === 'ER_BAD_NULL_ERROR' ||
      error instanceof TypeError
    ) {
      errorHandling.sendError(
        res,
        400,
        '파라미터가 잘못 되었습니다.',
        '파라미터가 잘못 되었습니다.',
      );
    } else {
      errorHandling.sendError(res, 500, '오류가 발생했습니다.', error);
    }
  }
});

/** 토큰 갱신, 유저 업데이트 */
router.patch('/social', async (req, res) => {
  try {
    const body = req.body;
    const jwtSecret = process.env.JWT_SECRET;
    if (!body.thumbnail_image) {
      body.thumbnail_image = body.profile_image;
    }
    const isUpdated = await update.updateSocial(body);
    if (isUpdated) {
      jwt.sign(
        {
          email: body.email,
          socialInfo: body,
          admin: false,
          type: 'social',
        },
        jwtSecret,
        {
          expiresIn: '2h',
          issuer: 'digicu',
          subject: 'userLogin',
        },
        (err, token) => {
          if (err) {
            errorHandling.sendError(res, 500, '토큰 발급 실패', error);
          }
          res.send({
            digicu_token: token,
            expires_in: 7200000, // 2h
          });
        },
      );
    } else {
      res.sendStatus(400);
    }
  } catch (error) {
    errorHandling.sendError(res, 500, '에러가 발생했습니다.', error);
  }
});

/** 업체 정보 변경 */
router.patch('/company', async (req, res) => {
  try {
    const {
      company_name,
      company_phone,
      company_address,
      company_owner,
      company_homepage,
      company_logo,
    } = req.body;
    const company_number = req.user.companyInfo.company_number;
    const isUpdated = await update.updateCompany({
      company_number,
      company_name,
      company_phone,
      company_address,
      company_owner,
      company_homepage,
      company_logo,
    });
    if (isUpdated) {
      res.sendStatus(200);
    } else {
      res.sendStatus(400);
    }
  } catch (error) {
    errorHandling.sendError(res, 500, '에러가 발생했습니다.', error);
  }
});

router.get('/company', async (req, res) => {
  try {
    const { include, count, page, orderby, desc } = req.query;
    const result = await search.findCompany(
      include,
      parseInt(count),
      parseInt(page),
      orderby,
      desc === 'true',
    );
    if (result) {
      res.send(result);
    } else {
      res.send([]);
    }
  } catch (e) {
    if (e.code === 'ER_PARSE_ERROR') {
      errorHandling.sendError(
        res,
        400,
        '파라미터가 잘못 되었습니다.',
        '파라미터가 잘못 되었습니다.',
      );
    } else {
      errorHandling.sendError(res, 500, '에러가 발생했습니다.', e);
    }
  }
});

module.exports = router;
