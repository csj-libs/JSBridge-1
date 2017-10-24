package com.xiansenliu.jstraw

import com.xiansenliu.jstraw.i.NativeHandler
import com.xiansenliu.jstraw.msg.Response

/**
 * Created by xinliu on 10/24/17.
 */
object UndefinedNativeHandler : NativeHandler<String, String> {
    override fun handle(data: String): Response<String> = Response(Response.STATUS_FAILED, "undefined handler", "")

    override fun description(): String = this::class.java.simpleName

}