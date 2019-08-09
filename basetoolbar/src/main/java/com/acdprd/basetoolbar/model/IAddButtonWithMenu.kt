package com.acdprd.basetoolbar.model

import android.view.View
import com.acdprd.basetoolbar.toolbar.BaseToolbar

interface IAddButtonWithMenu<TB, ACTION, ACTIONS, VIEW> where ACTION : Enum<ACTION>, ACTIONS : IContentActions<ACTION>, VIEW : View, VIEW : IOptionsView<ACTIONS, ACTION> {
    fun getOptionsView(): VIEW
    fun add(toolbarButton: TB, actions: ACTIONS, l: (ACTION) -> Unit): BaseToolbar<*, *>
}