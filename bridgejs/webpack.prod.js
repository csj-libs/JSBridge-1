const path = require('path');
const merge = require('webpack-merge');
const commom = require('./webpack.common');
const CleanWebpackPlugin = require('clean-webpack-plugin');
const UglifyjsWebpackPlugin = require('uglifyjs-webpack-plugin');
module.exports = merge(commom, {
  mode: 'production',
  entry: {
    bridgecore: './src/core/bridge.ts',
    bridgesdk: './src/sdk/bridgesdk.ts'
  },
  output: {
    filename: '[name].min.js',
    chunkFilename: '[name].min.js',
    libraryTarget: 'umd',
    path: path.resolve(__dirname, 'dist'),
  },
  plugins: [
    new CleanWebpackPlugin(['./dist']),
    new UglifyjsWebpackPlugin({
      parallel: 4
    }),
    new UglifyjsWebpackPlugin({
      parallel: 4
    })
  ],
});
