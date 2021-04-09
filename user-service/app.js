var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var cors = require('cors');

var testsRouter = require('./routes/tests');
var usersRouter = require('./routes/users');
var authRouter = require('./routes/authentication');

var app = express();

app.use(cors());
app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.set('views', './views');
app.set('view engine', 'ejs');

app.use('/users', usersRouter);
app.use('/authentication', authRouter);

if (process.env.NODE_ENV === 'development') {
  app.use('/tests', testsRouter);
}

module.exports = app;
