const merge = require('webpack-merge');
const common = require('./webpack.common.js');
const ParallelUglifyPlugin = require('webpack-parallel-uglify-plugin');
const PackageInfo = require('./package.json');
const version = PackageInfo.version;
module.exports = merge(common, {
  output: {
    filename: `[name].bundle-${version}.js`
  },
  plugins: [
    new ParallelUglifyPlugin({
      cacheDir: '.cache/',
      uglifyJS: {
        output: {
          comments: false
        },
        compress: {
          warnings: false
        }
      }
    })

  ]
});
