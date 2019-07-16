package com.acdprd.basetoolbar.toolbar

import android.content.Context
import android.content.res.TypedArray
import android.databinding.ViewDataBinding
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import com.acdprd.adapterandviews.view.CustomView
import com.acdprd.basetoolbar.model.IContentActions
import com.acdprd.basetoolbar.R
import com.acdprd.basetoolbar.helper.ToolbarHelper
import com.acdprd.basetoolbar.model.IAction

abstract class BaseToolbar<B : ViewDataBinding> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomView<B>(context, attrs, defStyleAttr) {

    protected var toolbarHelper:ToolbarHelper?=null

    init {
        val a: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.BaseToolbar, defStyleAttr, 0)
        val d = a.getString(R.styleable.BaseToolbar_android_text)
        if (d != null) setTitle(getTitleTv(), d)
        val lb = a.getResourceId(R.styleable.BaseToolbar_navButton, 0)
        if (lb != 0) setLeftButton(getLeftButton(),lb)
        a.recycle()
    }

    abstract fun getTitleTv(): TextView

    abstract fun getLeftButton():ImageView

    abstract fun getRightButtons():ViewGroup

    fun setTitle(tv: TextView, title: String?): BaseToolbar<B> {
        tv.visibility = VISIBLE
        tv.text = title
        return this
    }

    fun setTitle(tv: TextView, @StringRes title: Int): BaseToolbar<B> {
        tv.visibility = View.VISIBLE
        tv.setText(title)
        return this
    }


    fun setLeftButton(leftButton: ImageView, @DrawableRes icon: Int) {
        leftButton.visibility = View.VISIBLE
        leftButton.setImageDrawable(resources.getDrawable(icon))
    }


    fun setLeftButtonOnClickListener(v: View, l: () -> Unit) {
        v.setOnClickListener { l.invoke() }
    }

    /**
     * установить левую кнопку и слушатель
     */
    fun <E:Enum<E>> setLeftButton(iv:ImageView,toolbarButton: E, listener: () -> Unit): BaseToolbar<B> {
        iv.visibility = View.VISIBLE
        toolbarHelper?.getDrawableForButton(toolbarButton){
            iv.setImageDrawable(resources.getDrawable(it))
        }
        iv.setOnClickListener { listener.invoke() }
        return this
    }

    fun setLeftButton(@DrawableRes icon: Int, listener: () -> Unit): BaseToolbar<B> {
        getLeftButton().visibility = View.VISIBLE
        getLeftButton().setImageDrawable(resources.getDrawable(icon))
        getLeftButton().setOnClickListener { listener.invoke() }
        return this
    }

    fun hideLeftButton() {
        getLeftButton().visibility = View.GONE
    }

    /**
     * добавить кнопку справа у слушатель
     */
    fun <E:Enum<E>> addRightButton(tb: E, l: ()->Unit):BaseToolbar<B> {
        val b = ToolbarButtonWrapper(context)
        toolbarHelper?.getDrawableForButton(tb){
            b.setIcon(it)
        }

        b.setListener { l.invoke() }
        getRightButtons().addView(b)
        return this
    }

    fun  <E:Enum<E>> addRightButtonWithMenu(
        tb: E,
        actions: IContentActions,
        l: (IAction) -> Unit
    ): BaseToolbar<B> {
        val toolbarButtonWrapper = ToolbarButtonWrapper(context)
        toolbarHelper?.getDrawableForButton(tb){
            toolbarButtonWrapper.setIcon(it)
        }
        getRightButtons().addView(toolbarButtonWrapper)
        toolbarButtonWrapper.setListener {
            context?.let { c ->
                val optView = ToolbarOptionsView(c)
                var popMenu: PopupWindow? = null
                optView.setActionListener { o ->
                    popMenu?.dismiss()
                    l.invoke(o)
                }
                optView.setData(actions)
                popMenu = PopupWindow(
                    optView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                popMenu.isFocusable = true
                popMenu.showAsDropDown(toolbarButtonWrapper)
                popMenu.update()
            }
        }
        return this
    }

    /**
     * добавить кастомн
     */
    fun addRightCustomView(cv: CustomView<*>) {
        getRightButtons().addView(cv)
    }

    /**
     * добавляет кастомную вью в обертку, где слушатель на вьюхе
     */
    fun addRightCustomViewWithWrapper(cv: CustomView<*>) {
        val twv = ToolbarCustomViewWrapper(context)
        twv.addView(cv, width, height)
        getRightButtons().addView(twv)
    }

    /**
     * добавляет кастомную вью в обертку, где слушатель на обертке
     */
    fun addRightCustomViewWithWrapper(cv: CustomView<*>, l: View.OnClickListener) {
        val twv = ToolbarCustomViewWrapper(context)
        twv.addView(cv)
        twv.setOnClickListener(l)
        getRightButtons().addView(twv)
    }

//    fun setLeftCustomViewWithWrapper(cv: CustomView<*>, l: () -> Unit) {
//        val twv = ToolbarCustomViewWrapper(context)
//        twv.addView(cv)
//        twv.setOnClickListener { l.invoke() }
//        binding.frameLeftToolbar.visibility = View.VISIBLE
//        binding.frameLeftToolbar.removeAllViews()
//        binding.frameLeftToolbar.addView(twv, getParamsForGravityCenter())
//    }

    private fun getParamsForGravityCenter(): ViewGroup.LayoutParams {
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.CENTER
        return params
    }

    fun clearRightButtons() = getRightButtons().removeAllViews()
}