const path = require('path');


module.exports = {
  resolve: {
    extensions: [".ts", ".tsx", ".js", ".jsx", ".json"],
  },
  module: {
    rules: [
      {
        test: /\.(jsx?)|(tsx?)$/,
        use: [
          {
            loader: 'babel-loader',
            options: {
              babelrc: true,
            },
          }, {
            loader: 'awesome-typescript-loader'
          }
        ],
        exclude: [path.resolve(__dirname, 'node_modules')],
      }
    ],
  },
};
