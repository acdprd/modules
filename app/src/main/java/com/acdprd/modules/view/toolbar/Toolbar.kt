package com.acdprd.modules.view.toolbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.acdprd.basetoolbar.helper.AddRightButtonWithMenu
import com.acdprd.basetoolbar.model.IAddButtonWithMenu
import com.acdprd.basetoolbar.toolbar.BaseToolbar
import com.acdprd.modules.R
import com.acdprd.modules.databinding.ViewToolbarBinding
import com.acdprd.modules.helper.ToolbarHelper
import com.acdprd.modules.model.ToolbarButton
import com.acdprd.modules.model.action.Action
import com.acdprd.modules.model.action.ContentActions
import com.acdprd.modules.view.OptionsView

class Toolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseToolbar<ViewToolbarBinding, ToolbarButton>(context, attrs, defStyleAttr) {

    override fun getLayoutRes(): Int = R.layout.view_toolbar

    override fun getLeftButton(): ImageView = binding.leftButton

    override fun getRightButtons(): ViewGroup = binding.llRightButtons

    override fun getTitleTv(): TextView = binding.tvToolbarTitle

    override fun getDrawableForButton(toolbarButton: ToolbarButton): Drawable? {
       return context?.resources?.getDrawable(ToolbarHelper.getDrawableForButton(toolbarButton))
    }
}