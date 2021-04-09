const express = require('express');
const router = express.Router();
const { isLoggedIn } = require('../lib/middleware');

router.get('/form', (req, res) => {
  res.render('form-test');
});

router.post('/isLoggedIn', isLoggedIn, (req, res) => {
  res.sendStatus(200);
});

module.exports = router;
