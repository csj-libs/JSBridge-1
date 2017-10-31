package com.xiansenliu.jstraw.handler

import com.xiansenliu.jstraw.JSCallback

/**
 * Author       xinliu
 * Date         10/24/17
 * Time
 */
object UndefinedNativeHandler : NativeHandler<Any, String> {
    override fun handle(data: Any, callback: JSCallback<String>) {
        callback.failed("handler not found")
    }

    override fun name(): String = this::class.java.simpleName
}