const express = require('express');
const router = express.Router();
const { isLoggedIn } = require('../lib/middleware');

router.get('/form', (req, res) => {
  res.render('form-test');
});

module.exports = router;
