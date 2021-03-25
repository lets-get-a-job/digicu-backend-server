var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

const { usersAPIDocument } = require('./lib/swagger');
const swaggerUI = require('swagger-ui-express');

var usersRouter = require('./routes/users');

var app = express();

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.set('views', './views');
app.set('view engine', 'ejs');

app.use('/users', usersRouter);

app.use('/api-docs', swaggerUI.serve, swaggerUI.setup(usersAPIDocument));

module.exports = app;
