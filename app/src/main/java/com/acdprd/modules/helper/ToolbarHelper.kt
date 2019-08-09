package com.acdprd.modules.helper

import android.content.Context
import android.graphics.drawable.Drawable
import com.acdprd.basetoolbar.model.IToolbarHelper
import com.acdprd.modules.R
import com.acdprd.modules.model.ToolbarButton

object ToolbarHelper : IToolbarHelper<ToolbarButton>{
    override fun getDrawableForButton(toolbarButton: ToolbarButton): Int {
        return when(toolbarButton){
            ToolbarButton.BACK -> R.drawable.ic_back_black
            ToolbarButton.ARROW -> R.drawable.ic_arrow_right_black

            else ->0
        }
    }
}