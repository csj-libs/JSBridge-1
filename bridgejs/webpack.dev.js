const path = require('path');
const merge = require('webpack-merge');
const commom = require('./webpack.common');
const HtmlWebpackPlugin = require('html-webpack-plugin');
module.exports = merge(commom, {
  mode: 'development',
  entry: {
    index: './src/mock_index.jsx',
    bridgecore: './src/core/bridge.ts',
    bridgesdk: './src/sdk/bridgesdk.ts'
  },
  output: {
    filename: '[name].js',
    chunkFilename: '[name].js',
    path: path.resolve(__dirname, 'dist'),
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        use: ["source-map-loader"],
        enforce: "pre"
      }
    ]
  },
  devtool: 'inline-source-map',
  plugins: [
    new HtmlWebpackPlugin({
      title: 'BridgeJS',
      template: 'src/mock_index.ejs',
      filename: 'index.html',
      minify: {
        collapseWhitespace: true,
      },
    })
  ],
  devServer: {
    contentBase: path.join(__dirname, 'dist'),
    compress: true,
    port: 9090,
    historyApiFallback: true, // 解决刷新浏览器 404 问
  },
});
