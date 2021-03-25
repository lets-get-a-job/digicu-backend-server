const path = require('path');
const YAML = require('yamljs');
const usersAPIDocument = YAML.load(
  path.resolve(__dirname, 'docs', 'users.yml'),
);

module.exports = {
  usersAPIDocument,
};
