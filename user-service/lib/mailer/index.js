'use strict';
const nodemailer = require('nodemailer');
const Mail = require('nodemailer/lib/mailer');

// create reusable transporter object using the default SMTP transport
const transporter = nodemailer.createTransport({
  host: process.env.SMTP_HOST,
  port: process.env.SMTP_PORT,
  secure: process.env.SMTP_SSL === 'true',
  auth: {
    user: process.env.SMTP_USER,
    pass: process.env.SMTP_PASS,
  },
});

/**
 *
 * @callback SendMail
 * @param { Mail.Options } mailOptions
 * @returns { Promise<SentMessageInfo> }
 */

/**
 *
 * @type {SendMail}
 */
const sendMail = transporter.sendMail.bind(transporter);

module.exports = {
  sendMail,
};
