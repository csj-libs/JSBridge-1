package com.dashmrl.jsbridge

import com.dashmrl.jsbridge.callback.NativeCallback
import com.dashmrl.jsbridge.service.Service

/**
 * Author       xinliu
 * Date         10/22/17
 * Time         10:15 AM
 */
abstract class Bridge {

    abstract fun <T> callJS(service: String, param: String = "", cb: NativeCallback<T>): String

    abstract fun cancel(uuid: String)

    abstract fun registerService(service: Service<*, *>)

}