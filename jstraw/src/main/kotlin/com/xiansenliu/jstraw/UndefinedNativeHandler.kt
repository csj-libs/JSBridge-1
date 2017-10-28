package com.xiansenliu.jstraw

import com.xiansenliu.jstraw.callback.JSCallback
import com.xiansenliu.jstraw.i.NativeHandler

/**
 * Created by xinliu on 10/24/17.
 */
object UndefinedNativeHandler : NativeHandler<Any, String> {
    override fun handle(data: Any, callback: JSCallback<String>) {
        callback.failed("handler not found")
    }

    override fun name(): String = this::class.java.simpleName
}