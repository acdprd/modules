package com.acdprd.basetoolbar.toolbar

import android.content.Context
import android.content.res.TypedArray
import android.databinding.ViewDataBinding
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.acdprd.adapterandviews.view.CustomView
import com.acdprd.basetoolbar.R
import com.acdprd.basetoolbar.model.IToolbarHelper

abstract class BaseToolbar<B : ViewDataBinding, TB : Enum<TB>, TH : IToolbarHelper<TB>> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomView<B>(context, attrs, defStyleAttr) {

    private var toolbarHelper: TH = getToolbarHelper()

    init {
        readAttrs(attrs, defStyleAttr)
    }

    protected open fun readAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        val a: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.BaseToolbar, defStyleAttr, 0)
        val title = a.getString(R.styleable.BaseToolbar_android_text)
        if (title != null) setTitle(title)
        a.recycle()
    }


    abstract fun getTitleTv(): TextView

    abstract fun getLeftButton(): ImageView

    abstract fun getRightButtons(): ViewGroup

    abstract fun getToolbarHelper(): TH

    protected open fun setTitle(tv: TextView, title: String?): BaseToolbar<B, TB, TH> {
        tv.visibility = VISIBLE
        tv.text = title
        return this
    }

    protected open fun setTitle(title: String?): BaseToolbar<B, TB, TH> {
        getTitleTv().visibility = View.VISIBLE
        getTitleTv().text = title
        return this
    }

    protected open fun setLeftButton(
        leftButton: ImageView,
        icon: Drawable
    ): BaseToolbar<B, TB, TH> {
        leftButton.visibility = View.VISIBLE
        leftButton.setImageDrawable(icon)
        return this
    }

    protected open fun setLeftButton(
        toolbarButton: TB,
        listener: () -> Unit
    ): BaseToolbar<B, TB, TH> {
        getLeftButton().visibility = View.VISIBLE
        getLeftButton().setImageDrawable(toolbarHelper.getDrawableForButton(toolbarButton))
        getLeftButton().setOnClickListener { listener.invoke() }
        return this
    }

    protected open fun setLeftButton(toolbarButton: TB): BaseToolbar<B, TB, TH> {
        getLeftButton().visibility = View.VISIBLE
        getLeftButton().setImageDrawable(toolbarHelper.getDrawableForButton(toolbarButton))
        return this
    }

    protected open fun hideLeftButton() {
        getLeftButton().visibility = View.GONE
    }

    protected open fun setLeftButtonOnClickListener(l: () -> Unit): BaseToolbar<B, TB, TH> {
        getLeftButton().setOnClickListener { l.invoke() }
        return this
    }

    protected open fun addRightButton(tb: TB, l: () -> Unit): BaseToolbar<B, TB, TH> {
        val b = ToolbarButtonWrapper(context)
        b.setIcon(toolbarHelper.getDrawableForButton(tb))
        b.setListener(l)
        getRightButtons().addView(b)
        return this
    }

    protected open fun addRightCustomView(cv: CustomView<*>) {
        getRightButtons().addView(cv)
    }

    /**
     * добавляет кастомную вью в обертку, где слушатель на вьюхе
     */
    protected open fun addRightCustomViewWithWrapper(cv: CustomView<*>) {
        val twv = ToolbarCustomViewWrapper(context)
        twv.addView(cv, width, height) //todo ?
        getRightButtons().addView(twv)
    }

    /**
     * добавляет кастомную вью в обертку, где слушатель на обертке
     */
    protected open fun addRightCustomViewWithWrapper(cv: CustomView<*>, l: OnClickListener) {
        val twv = ToolbarCustomViewWrapper(context)
        twv.addView(cv)
        twv.setOnClickListener(l)
        getRightButtons().addView(twv)
    }

    protected open fun clearRightButtons() = getRightButtons().removeAllViews()

}