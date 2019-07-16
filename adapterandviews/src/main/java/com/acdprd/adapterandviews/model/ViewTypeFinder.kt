package com.acdprd.adapterandviews.model

import com.acdprd.adapterandviews.model.interfaces.IFindViewType
import com.acdprd.adapterandviews.model.interfaces.IViewType

class ViewTypeFinder<E>(var enums: Array<E>/*,var default:E*/) :
    IFindViewType<E> where E : Enum<E>, E : IViewType {

    override fun find(type: Int): E? {
        for (i in 0 until enums.size) {
            if (enums[i].getType() == type) return enums[i]
        }
        return null
    }
}