package com.acdprd.modules.model

import com.acdprd.adapterandviews.model.interfaces.IListItem
import com.acdprd.adapterandviews.model.interfaces.IViewType

interface ListItem : IListItem {
    override fun getItemType(): IViewType {
        return getViewType()
    }

    fun getViewType(): ViewType
}