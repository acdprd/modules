package com.acdprd.adapterandviews.model2

interface TypePositionConverter<T> {
    fun getTypeByViewType(position: Int): T?
    fun getViewTypeByType(type: T?): Int
}