const jwt = require('jsonwebtoken');
const { errorHandling } = require('../routing');

const isLoggedIn = async (req, res, next) => {
  try {
    const { token } = req.body;

    const verify = () =>
      new Promise((resolve, reject) =>
        jwt.verify(token, process.env.JWT_SECRET, (err, decoded) => {
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
      403,
      '인증에 실패했습니다.',
      '인증에 실패했습니다.',
    );
  }
};

module.exports = {
  isLoggedIn,
};
