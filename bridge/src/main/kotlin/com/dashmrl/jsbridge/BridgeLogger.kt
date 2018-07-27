package com.dashmrl.jsbridge

import android.util.Log

class BridgeLogger {

    companion object {
        private const val TAG = "JSBridge"
        fun d(msg: String) {
            Log.d(TAG, msg)
        }

        fun i(msg: String) {
            Log.i(TAG, msg)
        }

        fun w(msg: String) {
            Log.w(TAG, msg)
        }

        fun e(msg: String) {
            Log.e(TAG, msg)
        }
    }
}