const express = require('express');
const router = express.Router();

// NOTE 진짜 API는 기본 라이브러리가 다 작성된 이후 만들어질 것 입니다.
const {
  registerUser,
  removeUser,
  findUserByEmail,
  findUserByNick,
} = require('../lib/query-defintion');

router.get('/register_test', async (req, res) => {
  try {
    const isRegisterd = await registerUser(
      'test@test.com',
      'thisispassword1234',
      '테스트',
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
