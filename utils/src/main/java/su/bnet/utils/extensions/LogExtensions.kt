package su.bnet.utils.extensions

import android.util.Log

fun logw(tag: String, msg: String) {
    Log.w(tag, msg)
}

fun <T : Any> T.logd(message: String) {
    Log.d(tag(), message)
}

fun <T : Any> T.loge(message: String) {
    Log.e(tag(), message)
}

fun <T : Any> T.loge(message: String?, tr:Throwable) {
    Log.e(tag(), message, tr)
}

fun <T : Any> T.logw(message: String) {
    Log.w(tag(), message)
}

fun <T : Any> T.logw(message: String, tr:Throwable) {
    Log.w(tag(), message, tr)
}
