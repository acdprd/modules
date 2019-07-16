package com.acdprd.adapterandviews.model.interfaces

interface ListItem<VIEW_TYPE : IViewType> {
    fun getItemType(): VIEW_TYPE
}