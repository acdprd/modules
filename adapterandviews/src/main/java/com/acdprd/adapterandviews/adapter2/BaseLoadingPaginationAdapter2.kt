package com.acdprd.adapterandviews.adapter2

import com.acdprd.adapterandviews.model2.Item

abstract class BaseLoadingPaginationAdapter2<VIEW_TYPE, LIST_ITEM : Item<VIEW_TYPE>> :
    BasePaginationAdapter2<VIEW_TYPE, LIST_ITEM>() {
    abstract val loadingViewType: VIEW_TYPE
    abstract val loadingModel: LIST_ITEM

    override fun getLastItemPredicate(): (Int) -> Boolean =
        getDefaultLastItemPredicate(loadingViewType)

    private fun tryRemoveLastLoading() {
        if (_items.isNotEmpty() && _items.last().getItemType() == loadingViewType) {
            remove(_items.last())
        }
    }

    fun addLoading() {
        addItem(loadingModel)
    }

    override fun addNextPageItems(data: List<LIST_ITEM>, nextPage: Boolean) {
        tryRemoveLastLoading()
        super.addNextPageItems(data, nextPage)
        if (data.size >= limit) {
            addLoading()
        }
    }

    fun reset(lockLoading: Boolean) {
        if (lockLoading) {
            removeAll()
            paginator.loading()
            addLoading()
        } else {
            paginator.loadingOff()
        }
    }
}