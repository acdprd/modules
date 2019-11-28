package su.bnet.utils.extensions

import android.annotation.TargetApi
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.net.Uri
import android.os.Build
import android.support.annotation.*
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import su.bnet.utils.BuildConfig
import su.bnet.utils.Consts
import su.bnet.utils.R
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
fun TextView.recolor(start: Int, end: Int, @ColorRes color: Int, toUpperCase: Boolean = false):TextView =
    Utils.recolor(this, start, end, color, toUpperCase)

fun TextView.resize(start: Int, end: Int, @DimenRes size: Int):TextView = Utils.resize(this, start, end, size)

fun TextView.refont(start: Int, end: Int, ff: String):TextView = Utils.refont(this, start, end, ff)

fun TextView.refontCustom(start: Int, end: Int,@FontRes fontRes: Int):TextView = Utils.refontCustom(this, start, end, fontRes)

fun TextView.clickSpan(start: Int, end: Int, l: () -> Unit):TextView = Utils.setUnderLineWithListener(this, start, end, l)

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

/**
 * добавляет ripple эффект
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun <V : View> V.addRipple(@ColorRes color: Int): V {
    this.background?.let { back ->
        this.background = RippleDrawable(ColorStateList.valueOf(resources.getColor(color)), back, null)
    }
    return this
}
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun <V : View> V.addRippleColor(@ColorInt color: Int): V {
    this.background?.let { back ->
        this.background = RippleDrawable(ColorStateList.valueOf(color), back, null)
    }
    return this
}

/**
 * добавит rect со скругленными краями
 * по умолчанию белый цвет и скругления 2dp
 */
fun View.addBackgroundRect(@ColorRes solidColor: Int = R.color.white, @DimenRes cornersRadius: Int = R.dimen.size_2): View {
    val rect = getRectWithCorners(this.context, cornersRadius).also { drawable ->
        drawable.setColor(resources.getColor(solidColor))
    }
    this.background = rect
    return this
}

private fun getRectWithCorners(context: Context, @DimenRes cornerRadius: Int): GradientDrawable {
    val drawable = GradientDrawable()
    drawable.shape = GradientDrawable.RECTANGLE
    val cornerRadiusF = context.resources.getDimensionPixelSize(cornerRadius).toFloat()
    drawable.cornerRadii = floatArrayOf(
        cornerRadiusF,
        cornerRadiusF,
        cornerRadiusF,
        cornerRadiusF,
        cornerRadiusF,
        cornerRadiusF,
        cornerRadiusF,
        cornerRadiusF
    )
    return drawable
}
