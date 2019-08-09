package com.acdprd.basetoolbar.toolbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.acdprd.adapterandviews.view.CustomView
import com.acdprd.basetoolbar.R
import com.acdprd.basetoolbar.databinding.ViewButtonWrapperBinding


class ToolbarButtonWrapper @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomView<ViewButtonWrapperBinding>(context, attrs, defStyleAttr) {

    fun setListener(l: () -> Unit) {
        binding.buttonWrapper.setOnClickListener { l.invoke() }
    }

    fun setIcon(drawable: Drawable?) {
        binding.buttonWrapper.setIcon(drawable)
    }

    override fun getLayoutRes(): Int {
        return R.layout.view_button_wrapper
    }
}
