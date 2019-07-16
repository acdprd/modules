package com.acdprd.adapterandviews.model.interfaces

interface IFindViewType<VIEW_TYPE : IViewType> {
    fun find(type: Int): VIEW_TYPE?
}