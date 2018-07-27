package com.dashmrl.jsbridge.service

import com.dashmrl.jsbridge.callback.JSCallback

/**
 * Author       xinliu
 * Date         10/24/17
 * Time
 */
internal class FallbackService : Service<Any, String>() {
    override fun handle(data: Any, callback: JSCallback<String>) {
        callback.failed("service not found!!!")
    }
}