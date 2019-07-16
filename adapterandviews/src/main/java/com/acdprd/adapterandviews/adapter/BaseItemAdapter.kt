package com.acdprd.adapterandviews.adapter

import android.content.Context
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.acdprd.adapterandviews.model.interfaces.IFindViewType
import com.acdprd.adapterandviews.model.interfaces.IListItem
import com.acdprd.adapterandviews.model.interfaces.IViewType
import com.acdprd.adapterandviews.view.CustomListItemView
import java.util.*

/**
 * расширяет адаптер ItemsAdapter
 *
 * дополнительно не нужно переопрелять никаких методов кроме абстрактного
 * viewType как енам
 * каждая модель должна имплементить IListItem
 */
abstract class BaseItemAdapter :
    RecyclerView.Adapter<ItemHolder<ViewDataBinding, Any, out CustomListItemView<ViewDataBinding, Any>?>>() {
    protected var itemsClickListener: (IListItem) -> Unit = {}
    protected var _items = mutableListOf<IListItem>()

    override fun getItemCount(): Int = _items.size

    fun setItems(items: List<IListItem>) {
        this._items = items as ArrayList<IListItem>
        notifyDataSetChanged()
    }

    fun getItems(): List<Any> = _items

    abstract fun getViewTypeFinder(): IFindViewType

    protected abstract fun getCustomView(
        context: Context,
        viewType: IViewType
    ): View?

    protected fun getCustomView(
        context: Context,
        viewType: Int
    ): CustomListItemView<ViewDataBinding, Any>? {
        return getCustomView(
            context,
            getViewTypeFinder().find(viewType)
        ) as? CustomListItemView<ViewDataBinding, Any>
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemHolder<ViewDataBinding, Any, CustomListItemView<ViewDataBinding, Any>?> {
        val v = getCustomView(
            parent.context,
            getViewTypeFinder().find(viewType)
        ) as? CustomListItemView<ViewDataBinding, Any>

        return when (v == null) {
            true -> throw IllegalArgumentException("view is null")
            false -> ItemHolder(v)
        }
    }

    override fun onBindViewHolder(
        holder: ItemHolder<ViewDataBinding, Any, out CustomListItemView<ViewDataBinding, Any>?>,
        position: Int
    ) {
        val item = _items[position]
        holder.bindItem(item)
        holder.setItemClickListener(View.OnClickListener { itemsClickListener.invoke(item) })
    }

    override fun getItemViewType(position: Int): Int {
        return _items[position].getItemType().getType()
    }

    fun findViewType(pos: Int): IViewType {
        return getViewTypeFinder().find(getItemViewType(pos))
    }

    protected fun getItemView(holder: ItemHolder<*, Any, CustomListItemView<*, Any>>): CustomListItemView<*, Any> {
        return holder.getItemView()
    }

    fun findItemPosition(item: IListItem): Int = _items.indexOf(item)

    fun setItemClickListener(l: (IListItem) -> Unit) {
        itemsClickListener = l
    }

    fun addItems(itemList: List<IListItem>) {
        _items.addAll(itemList)
        notifyDataSetChanged()
    }

    fun addItems(position: Int, itemList: List<IListItem>) {
        _items.addAll(position, itemList)
        notifyDataSetChanged()
    }

    fun addItem(item: IListItem) {
        _items.add(item)
        notifyItemInserted(_items.size - 1)
    }

    fun addItem(position: Int, item: IListItem) {
        _items.add(position, item)
        notifyItemInserted(position)
    }

    fun removeAt(position: Int) {
        _items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun remove(item: IListItem) {
        val pos = findItemPosition(item)
        _items.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun removeAll() {
        _items.clear()
        notifyDataSetChanged()
    }

    fun swapElements(pos1: Int, pos2: Int) {
        Collections.swap(_items, pos1, pos2)
        notifyItemMoved(pos1, pos2)
    }

    fun getItem(position: Int): IListItem {
        return _items[position]
    }

    fun removeAllExcept(vararg types: IViewType) {
        for (item in _items.reversed()) {
            if (types.none { it == item.getItemType() }) {
                remove(item)
            }
        }
    }
}

