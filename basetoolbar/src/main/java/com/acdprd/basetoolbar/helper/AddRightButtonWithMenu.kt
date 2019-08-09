package com.acdprd.basetoolbar.helper

import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.acdprd.basetoolbar.model.*
import com.acdprd.basetoolbar.toolbar.BaseToolbar
import com.acdprd.basetoolbar.toolbar.ToolbarButtonWrapper

abstract class AddRightButtonWithMenu<TB, ACTION, ACTIONS, OV>(var toolbar: BaseToolbar<*, TB>) :
    IAddButtonWithMenu<TB, ACTION, ACTIONS, OV> where TB : Enum<TB>, ACTION : Enum<ACTION>, ACTIONS : IContentActions<ACTION>, OV : View, OV : IOptionsView<ACTIONS, ACTION> {

    override fun add(
        toolbarButton: TB,
        actions: ACTIONS,
        l: (ACTION) -> Unit
    ): BaseToolbar<*, *> {
        val toolbarButtonWrapper = ToolbarButtonWrapper(toolbar.context)
        toolbarButtonWrapper.setIcon(toolbar.getDrawableForButton(toolbarButton))
        toolbar.addRightCustomView(toolbarButtonWrapper)
        toolbarButtonWrapper.setListener {
            val optView = getOptionsView()
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
        return toolbar
    }
}