const path = require('path');
const merge = require('webpack-merge');
const common = require('./webpack.common.js');

module.exports = merge(common, {
  module: {
     loaders: [
         {
             test: /\.js$/,
             exclude: /node_modules/, // 这个模块下的js文件不通过babel转换
             loader: "babel-loader"
         }
     ]
   }
});
