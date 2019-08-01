package su.bnet.utils.extensions

import android.app.AlertDialog
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.support.annotation.StringRes
import android.util.Log
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import su.bnet.smartpot.BuildConfig
import su.bnet.smartpot.R
import su.bnet.smartpot.event.ErrorEvent
import su.bnet.smartpot.helpers.picasso.OfflineUtils
import su.bnet.smartpot.helpers.picasso.PicassoWrapper
import su.bnet.smartpot.model.Pot
import su.bnet.smartpot.network.exceptions.ApiErrorException
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

fun <T : Any> T.log(message: String) {
    Log.d(tag(), message)
}

fun <T : Any> T.err(message: String) {
    Log.e(tag(), message)
}

fun <T : Any> T.err(message: String?, tr:Throwable) {
    Log.e(tag(), message, tr)
}

fun <T : Any> T.warn(message: String) {
    Log.w(tag(), message)
}

fun <T : Any> T.warn(message: String, tr:Throwable) {
    Log.w(tag(), message, tr)
}
/**
 * logw gson.toJson(obj) в консоль
 */
fun <T : Any> T.print() = Log.w(tag(), Gson().toJson(this))

/**
 * logw gson.toJson(obj) в консоль
 */
fun <T : Any> T.asJson(): String = Gson().toJson(this)

/**
 * random char
 */
fun Random.nextChar(randomUppercase: Boolean = true) =
    if (randomUppercase && nextBoolean()) (nextInt(26) + 97).toChar().toUpperCase() else (nextInt(26) + 97).toChar()

fun kotlin.random.Random.nextChar(randomUppercase: Boolean = true) =
    if (randomUppercase && nextBoolean()) (nextInt(26) + 97).toChar().toUpperCase() else (nextInt(26) + 97).toChar()

/**
 * рандомный символ из кирриллицы (возможно не все)
 */
fun Random.nextCharRu(randomUppercase: Boolean = true): Char {
    val array = ArrayList<Char>()
    val ar2 = ArrayList<Int>(IntRange(1072, 1103).toMutableList())
    for (i in ar2) {
        array.add(i.toChar())
    }
    return array.randomElement()
}

fun kotlin.random.Random.nextCharRu(randomUppercase: Boolean = true): Char {
    val array = ArrayList<Char>()
    val ar2 = ArrayList<Int>(IntRange(1072, 1103).toMutableList())
    for (i in ar2) {
        array.add(i.toChar())
    }
    return array.randomElement()
}

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

fun eventError(e: Throwable) {
    if (e is ApiErrorException) {
        EventBus.getDefault().postSticky(ErrorEvent(e.apiError.error))
    } else {
        EventBus.getDefault().postSticky(ErrorEvent(e.message))
    }
}

fun <C : Context?> C.warning(
    title: String?,
    message: String,
    okButton: String,
    okListener: Function0<Unit>?,
    noButton: String?,
    noListener: Function0<Unit>?,
    cancelable: Boolean = true
) {
    this?.let {
        val builder = android.support.v7.app.AlertDialog.Builder(this)
        if (title != null) {
            builder.setTitle(title)
        }
        builder.setMessage(message)
        if (okListener != null) {
            builder.setPositiveButton(okButton) { d, w ->
                d.dismiss()
                okListener.invoke()
            }
        }
        if (noButton != null && noListener != null) {
            builder.setNegativeButton(noButton) { d, w ->
                d.dismiss()
                noListener.invoke()
            }
        }
        val b = builder.create()
        b.setOnShowListener {
            b.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.green_8b))
            b.getButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.gray_9a))
            b.getButton(android.support.v7.app.AlertDialog.BUTTON_NEUTRAL)
                .setTextColor(resources.getColor(R.color.gray_9a))
        }
        b.setCancelable(cancelable)
        b.show()
    }
}

fun <C : Context?> C.warning(@StringRes textId: Int) {
    this?.let {
        warning(this.resources.getString(textId))
    }
}

fun <C : Context?> C.warning(text: String) {
    this?.let {
        val adb = AlertDialog.Builder(this)
        adb.setMessage(text)
        adb.setPositiveButton("OK") { dialogInterface, i -> dialogInterface.dismiss() }
        val b = adb.create()
        b.setOnShowListener {
            b.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.green_8b))
            b.getButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.gray_9a))
            b.getButton(android.support.v7.app.AlertDialog.BUTTON_NEUTRAL)
                .setTextColor(resources.getColor(R.color.gray_9a))
        }
        b.show()
    }
}

fun wrapImage(imageUrl:String, pot: Pot, type: OfflineUtils.FileType):String{
    return PicassoWrapper.get().makeUrl(imageUrl, pot, type)
}

fun log(w:String){
    if (BuildConfig.DEV) Log.d("DEV log >",w)
}

fun log(p:Int){
    if (BuildConfig.DEV) Log.d("DEV log >",p.toString())
}