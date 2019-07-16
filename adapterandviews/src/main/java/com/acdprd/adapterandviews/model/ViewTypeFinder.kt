package com.acdprd.adapterandviews.model

import com.acdprd.adapterandviews.model.interfaces.IFindViewType
import com.acdprd.adapterandviews.model.interfaces.IViewType

class ViewTypeFinder<E>(var enums: Array<E>) :
    IFindViewType where E : Enum<E>, E : IViewType {

    override fun getDefault(): IViewType = BaseViewType.BASE_TYPE

    override fun find(type: Int): IViewType {
        for (i in 0 until enums.size) {
            if (enums[i].getType() == type) return enums[i]
        }
        return getDefault()
    }
}