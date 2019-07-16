package com.acdprd.modules.adapter

import com.acdprd.adapterandviews.adapter.BaseItemAdapter
import com.acdprd.adapterandviews.model.interfaces.IFindViewType
import com.acdprd.adapterandviews.model.ViewTypeFinder
import com.acdprd.modules.model.ViewType

abstract class ItemAdapter : BaseItemAdapter(){

    override fun getViewTypeFinder(): IFindViewType = ViewTypeFinder(ViewType.values())
}