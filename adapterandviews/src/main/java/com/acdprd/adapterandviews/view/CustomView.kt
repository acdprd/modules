package com.acdprd.adapterandviews.view

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout

abstract class CustomView<B : ViewDataBinding> : FrameLayout {

    protected lateinit var binding: B

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    constructor(context: Context) : super(context) {
        initBinding()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initBinding()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initBinding()
    }

    private fun initBinding() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(this.context), getLayoutRes(), this, true)
    }

    protected fun setMatchWrap() {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    protected fun setWrapWrap() {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    protected fun setMatchMatch() {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    protected fun setWrapMatch() {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
}
