const path = require('path');
const swaggerJSDoc = require('swagger-jsdoc');
const definition = {
  info: {
    title: 'User Service',
    version: '1.0.0',
    description: 'User service for Digicu Application',
  },
};
const usersRoutePath = path.resolve('..', '..', 'routes', 'users.js');
const options = {
  definition,
  apis: [usersRoutePath],
};

module.exports = swaggerJSDoc(options);
