package com.acdprd.adapterandviews.adapter2

import android.view.View
import com.acdprd.adapterandviews.helper.Paginator
import com.acdprd.adapterandviews.model2.Item
import com.acdprd.adapterandviews.view.CustomListItemView

abstract class BasePaginationAdapter2<VIEW_TYPE, LIST_ITEM> : BaseItemsCvAdapter2<VIEW_TYPE, LIST_ITEM, CustomListItemView<*,Any>>() where LIST_ITEM : Item<VIEW_TYPE>{
    private var nextPageListener: (Int) -> Unit = {}
    abstract val limit: Int
    protected open var paginator: Paginator = Paginator()
        .setItemsCountChecker { getOffset() }
        .setNextpageListener { nextPageListener.invoke(it) }
        .setLastItemPredicate { getLastItemPredicate().invoke(it) }

    override fun onBindViewHolder(
        holder: BaseItemHolder2<CustomListItemView<*, Any>>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)
        paginator.check(position)
    }

    open fun setNextPageListener(l: (Int) -> Unit) {
        nextPageListener = l
    }

    abstract fun getOffset(): Int

    abstract fun getLastItemPredicate(): (Int) -> Boolean

    open fun  addNextPageItems(
        data: List<LIST_ITEM>,
        nextPage: Boolean
    ) {
        if (nextPage) {
            addItems(data)
        } else {
            setItems(data)
        }
        paginator.loadingOff()
    }

    fun getDefaultLastItemPredicate(vt: VIEW_TYPE): (Int) -> Boolean {
        return LastItem(_items, vt)
    }

    inner class  LastItem<LIST_ITEM:Item<VIEW_TYPE>,VIEW_TYPE>(var data: MutableList<LIST_ITEM>, var lastItemType: VIEW_TYPE) :
            (Int) -> Boolean {
        override fun invoke(p: Int): Boolean {
            return data.isNotEmpty() && p == data.size - 1 && data[p].getItemType() == lastItemType
        }
    }
}