const path = require('path');
const CleanWebpackPlugin = require('clean-webpack-plugin');


module.exports = {
    entry: {
        native_mock: './src/js/native_mock.js',
        straw: ['./src/js/straw.js'],
        straw_ext: './src/js/straw_ext.js'
    },
    output: {
        filename: `[name].bundle.js`,
        path: path.resolve(__dirname, 'dist')
    },
    plugins: [
      //  new CleanWebpackPlugin(['dist'])
    ],
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /(node_modules)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['env']
                    }
                }
            }
        ]
    }
};
