package com.xiansenliu.jstraw.util

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Author       xinliu
 * Date         10/29/17
 * Time         4:53 PM
 */
class JsonUtil {
    companion object {
        private val gson by lazy { Gson() }

        internal inline fun <reified T> json2Obj(json: String): T {
            val type = object : TypeToken<T>() {}.type
            Log.d("JsonUtil", type.toString())
            return gson.fromJson<T>(json, type)
        }

        internal fun obj2Json(obj: Any): String {
            return gson.toJson(obj)
        }
    }
}