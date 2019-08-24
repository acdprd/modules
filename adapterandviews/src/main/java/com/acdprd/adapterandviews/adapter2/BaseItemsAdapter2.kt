package com.acdprd.adapterandviews.adapter2

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.acdprd.adapterandviews.model2.Item
import com.acdprd.adapterandviews.model2.TypePositionConverter
import java.util.*


abstract class BaseItemsAdapter2<VIEW_TYPE, LIST_ITEM : Item<VIEW_TYPE>, VIEW : View> :
    RecyclerView.Adapter<BaseItemHolder2<VIEW>>(), TypePositionConverter<VIEW_TYPE> {
    protected var _items = mutableListOf<LIST_ITEM>()
    protected var itemsClickListener: (LIST_ITEM) -> Unit = {}

    override fun getItemCount(): Int = _items.size

    override fun getItemViewType(position: Int): Int {
        return getViewTypeByType(_items[position].getItemType())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemHolder2<VIEW> {
        val v: VIEW? = getCustomView(
            parent.context,
            viewType
        )

        return when (v == null) {
            true -> throw IllegalArgumentException("view is null")
            false -> createViewHolder(v)
        }
    }

    override fun onBindViewHolder(
        holder: BaseItemHolder2<VIEW>,
        position: Int
    ) {
        val item = _items[position]
        bindItem(holder,item)
    }

    protected open fun getCustomView(context: Context, viewType: Int): VIEW? {
        return createView(
            context,
            getTypeByViewType(viewType)
        )
    }

    protected open fun createViewHolder(view: VIEW): BaseItemHolder2<VIEW>{
        return BaseItemHolder2(view)
    }

    // ===

    protected abstract fun createView(
        context: Context,
        viewType: VIEW_TYPE?
    ): VIEW?

    protected abstract fun bindItem(itemHolder: BaseItemHolder2<VIEW>, model: LIST_ITEM)

    // ===

    open fun setItemClickListener(l: (LIST_ITEM) -> Unit) {
        itemsClickListener = l
    }

    open fun getItems(): List<LIST_ITEM> = _items

    open fun setItems(items: List<LIST_ITEM>) {
        this._items = items.toMutableList()
        notifyDataSetChanged()
    }

    open fun findItemPosition(item: LIST_ITEM): Int = _items.indexOf(item)

    open fun addItems(itemList: List<LIST_ITEM>) {
        _items.addAll(itemList)
        notifyDataSetChanged()
    }

    open fun addItems(position: Int, itemList: List<LIST_ITEM>) {
        _items.addAll(position, itemList)
        notifyDataSetChanged()
    }

    open fun addItem(item: LIST_ITEM) {
        _items.add(item)
        notifyItemInserted(_items.size - 1)
    }

    open fun addItem(position: Int, item: LIST_ITEM) {
        _items.add(position, item)
        notifyItemInserted(position)
    }

    open fun removeAt(position: Int) {
        _items.removeAt(position)
        notifyItemRemoved(position)
    }

    open fun remove(item: LIST_ITEM) {
        val pos = findItemPosition(item)
        _items.removeAt(pos)
        notifyItemRemoved(pos)
    }

    open fun removeAll() {
        _items.clear()
        notifyDataSetChanged()
    }

    open fun swapElements(pos1: Int, pos2: Int) {
        Collections.swap(_items, pos1, pos2)
        notifyItemMoved(pos1, pos2)
    }

    open fun getItem(position: Int): LIST_ITEM {
        return _items[position]
    }

    @JvmOverloads
    open fun removeAllExcept(vararg types: VIEW_TYPE, reversed: Boolean = true) {
        for (item in if (reversed) _items.reversed() else _items) {
            if (types.none { it == item.getItemType() }) {
                remove(item)
            }
        }
    }
}