package com.acdprd.modules.model

import com.acdprd.adapterandviews.model.interfaces.IViewType

enum class ViewType : IViewType {
    FIRST,
    SECOND;

    override fun getType(): Int = ordinal
}