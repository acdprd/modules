package su.bnet.utils.helper

import android.util.Log
import su.bnet.utils.BuildConfig
import su.bnet.utils.extensions.tag

class Logg {
    companion object {

        @JvmOverloads
        fun i(tag: String = tag(), msg: String) {
            if (BuildConfig.DEBUG ) Log.i(tag, msg)
        }

        @JvmOverloads
        fun w(tag: String = tag(), msg: String) {
            if (BuildConfig.DEBUG ) Log.w(tag, msg)
        }

        @JvmOverloads
        fun d(tag: String = tag(), msg: String) {
            if (BuildConfig.DEBUG ) Log.d(tag, msg)
        }

        @JvmOverloads
        fun v(tag: String = tag(), msg: String) {
            if (BuildConfig.DEBUG ) Log.v(tag, msg)
        }
    }
}