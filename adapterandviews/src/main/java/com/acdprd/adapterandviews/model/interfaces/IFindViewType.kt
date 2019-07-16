package com.acdprd.adapterandviews.model.interfaces

interface IFindViewType {
    fun find(type:Int): IViewType

    fun getDefault(): IViewType
}