package com.acdprd.basetoolbar.toolbar


import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

import com.acdprd.adapterandviews.view.CustomView
import com.acdprd.basetoolbar.R
import com.acdprd.basetoolbar.databinding.ViewToolbarCvWrapperBinding


class ToolbarCustomViewWrapper @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomView<ViewToolbarCvWrapperBinding>(context, attrs, defStyleAttr) {

    override fun getLayoutRes(): Int = R.layout.view_toolbar_cv_wrapper


    fun addView(cv: CustomView<*>) {
        val lp = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            Gravity.CENTER
        )
        lp.gravity = Gravity.CENTER
        cv.layoutParams = lp
        binding.frameRoot.addView(cv)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        binding.frameRoot.setOnClickListener(l)
    }
}
