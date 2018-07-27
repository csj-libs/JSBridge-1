package com.dashmrl.jsbridge

import android.util.ArrayMap
import android.webkit.WebView
import com.dashmrl.jsbridge.callback.NativeCallback
import com.dashmrl.jsbridge.service.FallbackService
import com.dashmrl.jsbridge.service.Service
import com.dashmrl.jsbridge.service.ServiceName

/**
 * Author       xinliu
 * Date         10/22/17
 * Time         10:15 AM
 */
class BridgeImpl constructor(wv: WebView) : Bridge() {
    private val requestManger = RequestManager()
    private val services = ArrayMap<String, Service<*, *>>()
    private val proxy: Proxy = Proxy(wv, object : Proxy.Listener {
        override fun onRequest(service: String, request: String, proxy: Proxy) {
            (services[service] ?: FallbackService()).handleJSCall(request, proxy)
        }

        override fun onResponse(uuid: String, response: String) {
            requestManger.pickCallback(uuid)?.delivery(response)
        }
    })

    override fun <T> callJS(service: String, param: String, cb: NativeCallback<T>): String {
        val request = requestManger.newRequest(service, param, cb)
        proxy.request2JS(request)
        return request.uuid
    }

    override fun cancel(uuid: String) {
        requestManger.cancel(uuid)
    }

    override fun registerService(service: Service<*, *>) {
        if (service.javaClass.isAnnotationPresent(ServiceName::class.java)) {
            val name = service.javaClass.getAnnotation(ServiceName::class.java).name
            if (!name.isEmpty()) {
                if (services[name] == null) {
                    services[name] = service
                } else {
                    BridgeLogger.w("service named $name already exists")
                }
            } else {
                BridgeLogger.w("invalid name for $service")
            }
        } else {
            BridgeLogger.w("no ServiceName found on $service")
        }
    }


}