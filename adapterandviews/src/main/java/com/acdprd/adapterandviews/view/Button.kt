package com.acdprd.adapterandviews.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.util.TypedValue

open class Button @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    AppCompatImageView(context, attrs, defStyleAttr) {

    init {
        scaleType = ScaleType.CENTER_INSIDE
        setBackgroundBorderless(context)
    }

    private fun setBackgroundBorderless(context: Context) {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(getSelectableBackground(), typedValue, true)
        if (typedValue.resourceId != 0) {
            this.setBackgroundResource(typedValue.resourceId)
        }
    }

    protected open fun getSelectableBackground(): Int {
        return if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            android.R.attr.selectableItemBackground
        } else {
            android.R.attr.selectableItemBackgroundBorderless
        }
    }

    fun setIcon(drawable: Drawable?) {
        setImageDrawable(drawable)
    }
}
