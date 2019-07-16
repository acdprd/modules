package com.acdprd.adapterandviews.view

import android.content.Context
import android.databinding.ViewDataBinding
import android.util.AttributeSet

abstract class CustomListItemView<B : ViewDataBinding, M : Any> : CustomView<B> {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    abstract fun setData(model: M)
}
