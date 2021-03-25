const express = require('express');
const router = express.Router();

router.get('/form', (req, res) => {
  res.render('form-test');
});

module.exports = router;
