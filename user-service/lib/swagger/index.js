var express = require('express');
const path = require('path');
const YAML = require('yamljs');
const swaggerUI = require('swagger-ui-express');
const usersAPIDocument = YAML.load(
  path.resolve(__dirname, 'docs', 'User Service.yml'),
);
const app = express();

require('dotenv').config();

const port = process.env.API_DOCS_PORT || 3001;

app.use('/', swaggerUI.serve, swaggerUI.setup(usersAPIDocument));

app.listen(process.env.API_DOCS_PORT || 3001, () => {
  console.log(`API DOCS: http://localhost:${port}`);
});
