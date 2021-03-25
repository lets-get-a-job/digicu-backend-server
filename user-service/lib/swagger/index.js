const path = require('path');
const YAML = require('yamljs');
const usersAPIDocument = YAML.load(
  path.resolve(__dirname, 'docs', 'User Service.yml'),
);

module.exports = {
  usersAPIDocument,
};
