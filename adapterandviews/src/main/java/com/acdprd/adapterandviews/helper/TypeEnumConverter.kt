package com.acdprd.adapterandviews.helper

import com.acdprd.adapterandviews.model2.TypePositionConverter

class TypeEnumConverter <E:Enum<E>> (private val array:Array<E>) : TypePositionConverter<E>{
    override fun getTypeByViewType(position: Int): E? {
        return try {
            array[position]
        } catch (e: Exception) {
            null
        }
    }

    override fun getViewTypeByType(type: E?): Int {
       return array.asList().indexOf(type)
    }

}
