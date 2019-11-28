package su.bnet.utils.extensions

import android.app.AlertDialog
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.support.annotation.StringRes
import android.util.Log
import com.google.gson.Gson
import su.bnet.utils.BuildConfig
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*
import kotlin.collections.ArrayList

/** Получить md5 */
fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

/**
 * получить тэг для любого объекта, вернет Class.simpleName
 */
fun <T : Any> T.tag(): String = this.javaClass.simpleName

fun <T : Any> Class<T>.tag(): String = this.javaClass.simpleName


/**
 * logw gson.toJson(obj) в консоль
 */
fun <T : Any> T.print() = Log.w(tag(), Gson().toJson(this))

/**
 * logw gson.toJson(obj) в консоль
 */
fun <T : Any> T.asJson(): String = Gson().toJson(this)

/**
 * вернет процент этого числа от тотала
 */
fun <T : Number> T.percentageOf(total: Number) =
    if (total == 0) 0 else this.toInt() * 100 / total.toInt()

/**
 * очистит инт от часов\минут\секунд
 */
fun Int.clearHoursAndMinutes(): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = (this.toLong() * 1000)
    calendar.clear(Calendar.AM_PM)
    calendar.clear(Calendar.HOUR)
    calendar.clear(Calendar.HOUR_OF_DAY)
    calendar.clear(Calendar.MINUTE)
    calendar.clear(Calendar.SECOND)
    calendar.clear(Calendar.MILLISECOND)

    return (calendar.timeInMillis / 1000).toInt()
}

fun <T : Any?, R : Any?> T.reDeserialize(clazz: Class<R>): R =
    Gson().fromJson(Gson().toJson(this), clazz)

fun <L : LiveData<T>, T : Any> L.observeNotNull(owner: LifecycleOwner, observe: (T) -> Unit) {
    this.observe(owner, Observer<T> { o -> if (o != null) observe.invoke(o) })
}

fun <L : LiveData<T>, T : Any?> L.observeIt(owner: LifecycleOwner, observe: (T?) -> Unit) {
    this.observe(owner, Observer<T?> { o -> observe.invoke(o) })
}