const common = require('./webpack.common.js');
const merge = require('webpack-merge');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = merge(common, {
    output: {
        filename: '[name].bundle.[chunkhash].js',
    },
    plugins: [
        new HtmlWebpackPlugin({
            filename: "index.html"
        })
    ],
    devtool: 'inline-source-map',
    devServer: {
        contentBase: './dist'
    }
});
