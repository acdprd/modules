package su.bnet.utils.extensions

import android.content.Context
import android.graphics.Color
import android.support.annotation.StringRes
import android.widget.Toast
import su.bnet.utils.R

fun <C : Context?> C.alert(
    title: String?,
    message: String,
    okButton: String,
    okListener: (()->Unit)?,
    noButton: String?,
    noListener: (()->Unit)?,
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
                .setTextColor(Color.BLACK)
            b.getButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(Color.BLACK)
            b.getButton(android.support.v7.app.AlertDialog.BUTTON_NEUTRAL)
                .setTextColor(Color.BLACK)
        }
        b.setCancelable(cancelable)
        b.show()
    }
}

fun <C : Context?> C.alert(
    message: String,
    okListener: (()->Unit)?
) {
    this?.let {
        alert(null, message, getString(R.string.ok), okListener, null, null)
    }
}

fun <C : Context?> C.alert(
    message: Int,
    okListener: (()->Unit)?
) {
    this?.let {
        alert(null, getString(message), getString(R.string.ok), okListener, null, null)
    }
}

fun Context?.toast(text: String?) {
    this?.let {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}

fun Context?.toast(@StringRes textRes: Int) {
    this?.let {
        Toast.makeText(this, this.getString(textRes), Toast.LENGTH_SHORT).show()
    }
}