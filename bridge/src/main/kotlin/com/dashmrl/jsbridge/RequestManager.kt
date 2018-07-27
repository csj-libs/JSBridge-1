package com.dashmrl.jsbridge

import android.util.ArrayMap
import com.dashmrl.jsbridge.callback.NativeCallback
import com.dashmrl.jsbridge.model.Request
import java.util.*

internal class RequestManager {
    private val outgoingCallbacks = ArrayMap<String, NativeCallback<*>>()
    private val outgoingRequests = ArrayMap<String, Request<*>>()

    fun newRequest(service: String, param: String, cb: NativeCallback<*>): Request<String> {
        val uuid = UUID.randomUUID().toString()
        val req = Request.new(uuid, service, param)
        outgoingRequests[uuid] = req
        outgoingCallbacks[uuid] = cb
        return req
    }

    fun cancel(uuid: String) {
        outgoingCallbacks.remove(uuid)
        outgoingRequests.remove(uuid)
    }

    fun pickCallback(uuid: String): NativeCallback<*>? {
        outgoingRequests.remove(uuid)
        return outgoingCallbacks.remove(uuid)
    }


}