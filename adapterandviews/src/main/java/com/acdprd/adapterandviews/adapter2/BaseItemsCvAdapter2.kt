package com.acdprd.adapterandviews.adapter2

import android.content.Context
import android.view.View
import com.acdprd.adapterandviews.model2.Item
import com.acdprd.adapterandviews.view.CustomListItemView

abstract class BaseItemsCvAdapter2<VIEW_TYPE, LIST_ITEM, CUSTOM_VIEW> :
    BaseItemsAdapter2<VIEW_TYPE, LIST_ITEM, CUSTOM_VIEW>()
        where LIST_ITEM : Item<VIEW_TYPE>, CUSTOM_VIEW : CustomListItemView<*, *> {


    override fun bindItem(itemHolder: BaseItemHolder2<CUSTOM_VIEW>, model: LIST_ITEM) {
        val cv = itemHolder.itemView as? CustomListItemView<*, Any>
        cv?.let {
            cv.setData(model)
            itemHolder.setItemClickListener(View.OnClickListener { itemsClickListener.invoke(model) })
        }
    }

    override fun createView(context: Context, viewType: VIEW_TYPE?): CUSTOM_VIEW? {
        return createCustomView(context, viewType)
    }

    abstract fun createCustomView(context: Context, viewType: VIEW_TYPE?): CUSTOM_VIEW?

//    override fun createViewHolder(view: CUSTOM_VIEW): ItemHolder2<CUSTOM_VIEW> {
//        return ItemHolder2(view)
//    }
}