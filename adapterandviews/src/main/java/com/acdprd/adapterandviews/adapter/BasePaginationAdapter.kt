package com.acdprd.adapterandviews.adapter

import android.databinding.ViewDataBinding
import com.acdprd.adapterandviews.helper.Paginator
import com.acdprd.adapterandviews.model.interfaces.IListItem
import com.acdprd.adapterandviews.model.interfaces.IViewType
import com.acdprd.adapterandviews.view.CustomListItemView

abstract class BasePaginationAdapter<VT> :
    BaseItemAdapter<VT>() where VT : Enum<VT>, VT : IViewType {
    private var nextPageListener: (Int) -> Unit = {}
    abstract val limit: Int
    protected open var paginator: Paginator = Paginator()
        .setItemsCountChecker { getOffset() }
        .setNextpageListener { nextPageListener.invoke(it) }
        .setLastItemPredicate { getLastItemPredicate().invoke(it) }

    override fun onBindViewHolder(
        holder: ItemHolder<ViewDataBinding, Any, out CustomListItemView<ViewDataBinding, Any>?>,
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

    open fun <LIST_ITEM : IListItem<VT>> addNextPageItems(
        data: MutableList<LIST_ITEM>,
        nextPage: Boolean
    ) {
        if (nextPage) {
            addItems(data)
        } else {
            setItems(data)
        }
        paginator.loadingOff()
    }

    fun getDefaultLastItemPredicate(vt: VT): (Int) -> Boolean {
        return LastItem(_items, vt)
    }

    inner class LastItem<T : IListItem<VT>>(var data: MutableList<T>, var lastItemType: VT) :
            (Int) -> Boolean {
        override fun invoke(p: Int): Boolean {
            return data.isNotEmpty() && p == data.size - 1 && data[p].getItemType() == lastItemType
        }
    }
}