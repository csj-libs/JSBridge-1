package com.xiansenliu.jstraw.util

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.WebView

/**
 * Author       xinliu
 * Date         10/23/17
 * Time
 */
class InteractUtil {
    companion object {
        internal fun callJS(wv: WebView, request: String) {
            execOnMain { wv.loadUrl("javascript:window.callFromNative($request)") }
        }

        internal fun response2JS(wv: WebView, callbackId: Int, response: String) {
            execOnMain { wv.loadUrl("javascript:responseFromNative($callbackId,$response)") }
        }


        private inline fun execOnMain(crossinline f: () -> Unit) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                f()
            } else {
                Handler(Looper.getMainLooper()).post {
                    f()
                }
            }
        }
    }
}


