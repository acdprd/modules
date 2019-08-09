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

abstract class BaseToolbar<B : ViewDataBinding, TB : Enum<TB>> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomView<B>(context, attrs, defStyleAttr) {

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


   protected abstract fun getTitleTv(): TextView

    protected  abstract fun getLeftButton(): ImageView

    protected  abstract fun getRightButtons(): ViewGroup

    abstract fun getDrawableForButton(toolbarButton:TB): Drawable?

    protected open fun setTitle(tv: TextView, title: String?): BaseToolbar<B, TB> {
        tv.visibility = VISIBLE
        tv.text = title
        return this
    }

     open fun setTitle(title: String?): BaseToolbar<B, TB> {
        getTitleTv().visibility = View.VISIBLE
        getTitleTv().text = title
        return this
    }

     open fun setLeftButton(
        leftButton: ImageView,
        icon: Drawable
    ): BaseToolbar<B, TB> {
        leftButton.visibility = View.VISIBLE
        leftButton.setImageDrawable(icon)
        return this
    }

     open fun setLeftButton(
        toolbarButton: TB,
        listener: () -> Unit
    ): BaseToolbar<B, TB> {
        getLeftButton().visibility = View.VISIBLE
        getLeftButton().setImageDrawable(getDrawableForButton(toolbarButton))
        getLeftButton().setOnClickListener { listener.invoke() }
        return this
    }

     open fun setLeftButton(toolbarButton: TB): BaseToolbar<B, TB> {
        getLeftButton().visibility = View.VISIBLE
        getLeftButton().setImageDrawable(getDrawableForButton(toolbarButton))
        return this
    }

     open fun hideLeftButton() {
        getLeftButton().visibility = View.GONE
    }

     open fun setLeftButtonOnClickListener(l: () -> Unit): BaseToolbar<B, TB> {
        getLeftButton().setOnClickListener { l.invoke() }
        return this
    }

     open fun addRightButton(tb: TB, l: () -> Unit): BaseToolbar<B, TB> {
        val b = ToolbarButtonWrapper(context)
        b.setIcon(getDrawableForButton(tb))
        b.setListener(l)
        getRightButtons().addView(b)
        return this
    }

     open fun addRightCustomView(cv: View) {
        getRightButtons().addView(cv)
    }

    /**
     * добавляет кастомную вью в обертку, где слушатель на вьюхе
     */
     open fun addRightCustomViewWithWrapper(cv: CustomView<*>) {
        val twv = ToolbarCustomViewWrapper(context)
        twv.addView(cv, width, height) //todo ?
        getRightButtons().addView(twv)
    }

    /**
     * добавляет кастомную вью в обертку, где слушатель на обертке
     */
     open fun addRightCustomViewWithWrapper(cv: CustomView<*>, l: OnClickListener) {
        val twv = ToolbarCustomViewWrapper(context)
        twv.addView(cv)
        twv.setOnClickListener(l)
        getRightButtons().addView(twv)
    }

     open fun clearRightButtons() = getRightButtons().removeAllViews()

}