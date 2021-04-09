module.exports = {
  /**
   *
   * @param {Response} res
   * @param {number} status
   * @param {any} errorMessage
   * @param {any} debugMessage
   * @param {object} headers
   */
  sendError(res, status, errorMessage, debugMessage, headers) {
    res.status(status);
    if (process.env.NODE_ENV === 'development') {
      console.log(`error message: ${debugMessage}`);
      res.send(`error message: ${debugMessage}`);
    } else {
      res.set;
      res.send(errorMessage);
    }
  },
  /**
   *
   * @param {Response} res
   * @param {number} status
   * @param {any} errorMessage
   * @param {any} debugMessage
   */
  sendErrorJSON(res, status, errorMessage, debugMessage) {
    res.status(status);
    if (process.env.NODE_ENV === 'development') {
      console.log(`error message: ${debugMessage}`);
      res.send(`error message: ${debugMessage}`);
    } else {
      res.json(errorMessage);
    }
  },
};
