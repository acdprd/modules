package com.acdprd.modules.model

class ModelF(var text: String) : ListItem {
    override fun getViewType(): ViewType = ViewType.FIRST
}

class ModelS(var text: String) : ListItem {
    override fun getViewType(): ViewType = ViewType.SECOND
}