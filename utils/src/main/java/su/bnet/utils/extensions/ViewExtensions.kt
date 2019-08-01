package su.bnet.utils.extensions

import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import su.bnet.utils.Consts
import su.bnet.utils.utils.AnimationUtils
import su.bnet.utils.utils.ImageUtils
import su.bnet.utils.utils.Utils
import su.bnet.utils.viewlisteners.AnimationEndListener

/**
 * только цифры из editText
 */
fun EditText.onlyDigits(): String = Utils.onlyDigits(this.text.toString())

/**
 * загрузить пикассой
 */
fun ImageView.loadByPicasso(url: String?, @DrawableRes id: Int): Unit =
    ImageUtils.loadImage(context, url, this, id)

/**
 * загрузить пикассой
 * d - placeholder
 */
fun ImageView.loadByPicasso(url: String?, d: Drawable): Unit = ImageUtils.loadImage(url, this, d)

/**
 * загрузить пикассой
 */
fun ImageView.loadByPicasso(url: String?): Unit = ImageUtils.loadImage(url, this)

fun ImageView.loadByPicasso(url: Uri, size: Long): Unit = ImageUtils.loadImage(url, this, size)



/**
 * изменить цвет текста с\ по
 * toUppercase - если нужен капс текст (капс как атрибут стирает цвета)
 */
fun TextView.recolor(start: Int, end: Int, @ColorRes color: Int, toUpperCase: Boolean = false) =
    Utils.recolor(this, start, end, color, toUpperCase)

fun <T : View> T.collapse(
    duration: Number = Consts.DEFAULT_ANIM_TIME,
    listener: AnimationEndListener? = null
) =
    AnimationUtils.collapse(this, duration.toLong(), listener)

fun <T : View> T.expand(
    duration: Number = Consts.DEFAULT_ANIM_TIME,
    listener: AnimationEndListener? = null
) =
    AnimationUtils.expand(this, duration.toLong(), listener)

//fun animateTransition(vg: ViewGroup, work: () -> Unit) {
//    TransitionManager.beginDelayedTransition(vg)
//    work.invoke()
//}
