package com.acdprd.modules.adapter

import com.acdprd.adapterandviews.adapter.BaseItemAdapter
import com.acdprd.adapterandviews.model.ViewTypeFinder
import com.acdprd.adapterandviews.model.interfaces.IFindViewType
import com.acdprd.modules.model.ViewType

abstract class ItemAdapter : BaseItemAdapter<ViewType>() {
    override fun getViewTypeFinder(): IFindViewType<ViewType> {
        return ViewTypeFinder(ViewType.values())
    }
}