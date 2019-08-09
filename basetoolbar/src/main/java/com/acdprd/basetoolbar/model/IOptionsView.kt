package com.acdprd.basetoolbar.model

interface IOptionsView<ACTIONS, ACTION> where ACTION : Enum<ACTION>, ACTIONS : IContentActions<ACTION> {
    fun setActionListener(l: (ACTION) -> Unit)
    fun setData(model: ACTIONS)
}