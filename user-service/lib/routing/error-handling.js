module.exports = {
  /**
   *
   * @param {Response} res
   * @param {number} status
   * @param {any} errorMessage
   * @param {any} debugMessage
   * @param {any} headers
   */
  sendError(res, status, errorMessage, debugMessage, headers) {
    res.status(status);
    if (process.env.NODE_ENV === 'development') {
      res.send(`error message: ${debugMessage}`);
    } else {
      if (headers) {
        res.set(headers);
      }
      res.send(errorMessage);
    }
  },
};
