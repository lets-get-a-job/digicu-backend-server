module.exports = {
  /**
   *
   * @param {express.Response} res
   * @param {number} status
   * @param {any} errorMessage
   * @param {any} debugMessage
   */
  sendError(res, status, errorMessage, debugMessage) {
    res.status(status);
    if (process.env.NODE_ENV === 'development') {
      console.log(`error message: ${debugMessage}`);
      res.send(`error message: ${debugMessage}`);
    } else {
      res.send(errorMessage);
    }
  },
};
