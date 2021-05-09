const jwt = require('jsonwebtoken');
const { errorHandling } = require('../routing');
const { Request, Response, NextFunction } = require('express');

/**
 *
 * @param {Request} req
 * @param {Response} res
 * @param {NextFunction} next
 */
const isLoggedIn = async (req, res, next) => {
  try {
    const { digicu_token } = req.headers;

    const verify = () =>
      new Promise((resolve, reject) =>
        jwt.verify(digicu_token, process.env.JWT_SECRET, (err, decoded) => {
          if (err) reject(err);
          resolve(decoded);
        }),
      );

    const user = await verify();
    req.user = user;

    next();
  } catch (error) {
    errorHandling.sendError(
      res,
      401,
      '인증에 실패했습니다.',
      '인증에 실패했습니다.',
    );
  }
};

module.exports = {
  isLoggedIn,
};
