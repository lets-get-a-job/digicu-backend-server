var express = require('express');
const path = require('path');
const YAML = require('yamljs');
const swaggerUI = require('swagger-ui-express');
const usersAPIDocument = YAML.load(
  path.resolve(__dirname, 'docs', 'User Service.yml'),
);
const app = express();

require('dotenv').config();

app.use('/', swaggerUI.serve, swaggerUI.setup(usersAPIDocument));

app.listen(process.env.API_DOCS_PORT || 4000, () => {
  console.log(`API DOCS: http://localhost:${process.env.API_DOCS_PORT}`);
});
