package com.acdprd.modules.adapter

import android.content.Context
import android.databinding.ViewDataBinding
import android.view.View
import com.acdprd.adapterandviews.adapter2.BaseItemsCvAdapter2
import com.acdprd.adapterandviews.helper.TypeEnumConverter
import com.acdprd.adapterandviews.model2.TypePositionConverter
import com.acdprd.adapterandviews.view.CustomListItemView
import com.acdprd.modules.model.ListItem2
import com.acdprd.modules.model.ViewType2

abstract class ItemAdapter2 :
    BaseItemsCvAdapter2<ViewType2, ListItem2, CustomListItemView<*, *>>() {
    private val converter: TypePositionConverter<ViewType2> = TypeEnumConverter(ViewType2.values())

    override fun getTypeByViewType(position: Int): ViewType2? =
        converter.getTypeByViewType(position)

    override fun getViewTypeByType(type: ViewType2?): Int = converter.getViewTypeByType(type)
}