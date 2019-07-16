package com.acdprd.modules.model

class ModelF(var text: String) : ListItem {
    override fun getItemType(): ViewType = ViewType.FIRST

}

class ModelS(var text: String) : ListItem {
    override fun getItemType(): ViewType = ViewType.SECOND
}
