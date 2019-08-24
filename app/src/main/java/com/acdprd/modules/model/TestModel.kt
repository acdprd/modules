package com.acdprd.modules.model

class ModelF(var text: String) : ListItem2 {
    override fun getItemType(): ViewType2 = ViewType2.FIRST2

}

class ModelS(var text: String) : ListItem2 {
    override fun getItemType(): ViewType2 = ViewType2.SECOND2
}
