const express = require('express');
const router = express.Router();

// NOTE 진짜 API는 기본 라이브러리가 다 작성된 이후 만들어질 것 입니다.
const {
  registerUser,
  removeUser,
  findUserByEmail,
  findUserByNick,
  confirmEmailAuth,
  changePassword,
  createChangePasswordToken,
} = require('../lib/query-defintion');

router.get('/:email/auth', (req, res) => {
  if (req.query.type === 'reg' && req.query.token) {
    res.render('email-auth', {
      email: req.params.email,
      token: req.query.token,
    });
  } else {
    res.sendStatus(400);
  }
});

router.post('/:email/auth', async (req, res) => {
  if (req.query.type === 'pwd') {
    const token = await createChangePasswordToken(
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
});

router.put('/:email/auth', async (req, res) => {
  try {
    if (req.query.type === 'reg') {
      const isAuthOk = await confirmEmailAuth(
        req.params.email,
        req.query.type,
        req.body.token,
      );
      if (isAuthOk) {
        res.sendStatus(200);
      } else {
        res.sendStatus(400);
      }
    } else if (req.query.type === 'pwd') {
      const isPasswordChanged = await changePassword(
        req.params.email,
        req.body.new_encpwd,
        'pwd',
        req.body.token,
      );
      if (isPasswordChanged) {
        res.sendStatus(200);
      } else {
        res.sendStatus(400);
      }
    }
  } catch (error) {
    res.status(400);
    if (process.env.NODE_ENV === 'development') {
      res.send(`error message: ${error}`);
    } else {
      res.send('인증 실패');
    }
  }
});

router.get('/change_password_test', async (req, res) => {
  res.render('change-password-test', {
    email: 'formail0001@naver.com',
  });
});

router.get('/register_test', async (req, res) => {
  try {
    const isRegisterd = await registerUser(
      'formail0001@naver.com',
      'thisispassword1234',
      '테스트',
      'manager',
      true,
    );
    if (isRegisterd) {
      res.send('유저 등록 성공');
    } else {
      res.status(400);
      res.send('유저 등록 실패');
    }
  } catch (error) {
    res.status(400);
    if (process.env.NODE_ENV === 'development') {
      res.send(`error message: ${error}`);
    } else {
      res.send('유저 등록 실패');
    }
  }
});

router.get('/remove_test', async (req, res) => {
  try {
    const isRemoved = await removeUser('test@test.com');
    if (isRemoved) {
      res.send('유저 삭제 성공');
    } else {
      res.status(400);
      res.send('유저 삭제 실패');
    }
  } catch (error) {
    res.status(400);
    if (process.env.NODE_ENV === 'development') {
      res.send(`error message: ${error}`);
    } else {
      res.send('유저 삭제 실패');
    }
  }
});

router.get('/find_test', async (req, res) => {
  try {
    const user1 = await findUserByEmail('test@test.com');
    const user2 = await findUserByNick('테스트');
    const no_user1 = await findUserByEmail('foo@foo.com');
    const no_user2 = await findUserByNick('테스트2');
    res.json(JSON.stringify({ user1, user2, no_user1, no_user2 }));
  } catch (error) {
    res.status(400);
    if (process.env.NODE_ENV === 'development') {
      res.send(`error message: ${error}`);
    } else {
      res.send('유저 등록 실패');
    }
  }
});

module.exports = router;
