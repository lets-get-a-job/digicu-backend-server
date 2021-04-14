const express = require('express');
const router = express.Router();

const { authentication, search } = require('../lib/query/users');
const { errorHandling } = require('../lib/routing');
const { isLoggedIn } = require('../lib/middleware');
const bcrypt = require('bcrypt');
const crypto = require('crypto');
const jwt = require('jsonwebtoken');

router.post('/login', async (req, res) => {
  const { email, plain_password } = req.body;
  const jwtSecret = process.env.JWT_SECRET;
  try {
    const { type, hash_string } = await search.findRegistrationByEmail(
      email,
      true,
    );
    if (bcrypt.compareSync(plain_password, hash_string)) {
      if (await authentication.isEmailChecked(email)) {
        jwt.sign(
          {
            email,
            admin: type === 'admin',
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
              token,
              type,
              expires_in: 7200000, // 2h
            });
          },
        );
      } else {
        errorHandling.sendError(
          res,
          401,
          '이메일 인증되지 않은 아이디 입니다.',
          '이메일 인증되지 않은 아이디 입니다.',
        );
      }
    } else {
      errorHandling.sendError(
        res,
        403,
        '아이디 또는 패스워드가 다릅니다.',
        '아이디 또는 패스워드가 다릅니다.',
      );
    }
  } catch (error) {
    if (error instanceof TypeError) {
      errorHandling.sendError(
        res,
        403,
        '아이디 또는 패스워드가 다릅니다.',
        '아이디 또는 패스워드가 다릅니다.',
      );
    } else {
      errorHandling.sendError(res, 500, '에러가 발생했습니다.', error);
    }
  }
});

router.post('/login/refresh', isLoggedIn, (req, res) => {
  jwt.sign(
    {
      email,
      admin: type === 'admin',
      type,
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
        token,
        type,
        expires_in: 7200000, // 2h
      });
    },
  );
});

router.get('/:email', (req, res) => {
  if (req.query.type === 'reg' && req.query.token) {
    res.render('email-auth', {
      email: req.params.email,
      token: req.query.token,
    });
  } else {
    res.sendStatus(400);
  }
});

router.post('/:email', async (req, res) => {
  try {
    if (req.query.type === 'pwd') {
      const token = await authentication.createChangePasswordToken(
        req.params.email,
        req.body.old_encpwd,
      );
      if (token) {
        res.json({ token });
      } else {
        res.sendStatus(400);
      }
    } else {
      res.sendStatus(400);
    }
  } catch (error) {
    errorHandling.sendError(res, 500, '에러가 발생했습니다.', error);
  }
});

router.patch('/:email', async (req, res) => {
  try {
    if (req.query.type === 'reg') {
      const isAuthOk = await authentication.confirmEmailAuth(
        req.params.email,
        req.body.token,
      );
      if (isAuthOk) {
        res.sendStatus(200);
      } else {
        res.sendStatus(400);
      }
    } else if (req.query.type === 'pwd') {
      const isPasswordChanged = await authentication.changePassword(
        req.params.email,
        req.body.new_encpwd,
        req.body.token,
      );
      if (isPasswordChanged) {
        res.sendStatus(200);
      } else {
        res.sendStatus(400);
      }
    }
  } catch (error) {
    errorHandling.sendError(res, 500, '에러가 발생했습니다.', error);
  }
});

module.exports = router;
