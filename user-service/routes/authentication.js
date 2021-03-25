const express = require('express');
const router = express.Router();

const { authentication } = require('../lib/query/users');
const { errorHandling } = require('../lib/routing');

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
});

router.put('/:email', async (req, res) => {
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
    errorHandling.sendError(res, 400, '인증 실패', error);
  }
});
