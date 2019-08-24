package com.acdprd.adapterandviews.adapter

import android.content.Context
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.acdprd.adapterandviews.model.ViewTypeFinder
import com.acdprd.adapterandviews.model.interfaces.IFindViewType
import com.acdprd.adapterandviews.model.interfaces.IViewType
import com.acdprd.adapterandviews.model.interfaces.IListItem
import com.acdprd.adapterandviews.view.CustomListItemView
import java.util.*

/**
 * расширяет адаптер ItemsAdapter
 *
 * дополнительно не нужно переопрелять никаких методов кроме абстрактного
 * viewType как енам
 * каждая модель должна имплементить ListItem
 */
abstract class BaseItemAdapter<VIEW_TYPE> :
    RecyclerView.Adapter<ItemHolder<ViewDataBinding, Any, out CustomListItemView<ViewDataBinding, Any>?>>() where VIEW_TYPE : IViewType, VIEW_TYPE : Enum<VIEW_TYPE> {
    protected var itemsClickListener: (IListItem<VIEW_TYPE>) -> Unit = {}
    protected var _items = mutableListOf<IListItem<VIEW_TYPE>>()

    override fun getItemCount(): Int = _items.size

    fun setItems(items: List<IListItem<VIEW_TYPE>>) {
        this._items = items as MutableList<IListItem<VIEW_TYPE>>
        notifyDataSetChanged()
    }

    fun getItems(): List<Any> = _items

    abstract fun getViewTypeFinder(): IFindViewType<VIEW_TYPE>

//    private inline fun <reified E : Enum<E>> getEnumAsArray(): Array<E> {
//        return enumValues()
//    }

    protected abstract fun getCustomView(
        context: Context,
        viewType: VIEW_TYPE?
    ): View?

//    protected fun getCustomView(
//        context: Context,
//        viewType: Int
//    ): CustomListItemView<ViewDataBinding, Any>? {
//        return getCustomView(
//            context,
//            getViewTypeFinder().find(viewType)
//        ) as? CustomListItemView<ViewDataBinding, Any>
//    }

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

    fun findViewType(pos: Int): VIEW_TYPE? {
        return getViewTypeFinder().find(getItemViewType(pos))
    }

    protected fun getItemView(holder: ItemHolder<*, Any, CustomListItemView<*, Any>>): CustomListItemView<*, Any> {
        return holder.getItemView()
    }

    fun findItemPosition(item: IListItem<VIEW_TYPE>): Int = _items.indexOf(item)

    fun setItemClickListener(l: (IListItem<VIEW_TYPE>) -> Unit) {
        itemsClickListener = l
    }

    fun addItems(itemList: List<IListItem<VIEW_TYPE>>) {
        _items.addAll(itemList)
        notifyDataSetChanged()
    }

    fun addItems(position: Int, itemList: List<IListItem<VIEW_TYPE>>) {
        _items.addAll(position, itemList)
        notifyDataSetChanged()
    }

    fun addItem(item: IListItem<VIEW_TYPE>) {
        _items.add(item)
        notifyItemInserted(_items.size - 1)
    }

    fun addItem(position: Int, item: IListItem<VIEW_TYPE>) {
        _items.add(position, item)
        notifyItemInserted(position)
    }

    fun removeAt(position: Int) {
        _items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun remove(item: IListItem<VIEW_TYPE>) {
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

    fun getItem(position: Int): IListItem<VIEW_TYPE> {
        return _items[position]
    }

    fun removeAllExcept(vararg types: VIEW_TYPE) {
        for (item in _items.reversed()) {
            if (types.none { it == item.getItemType() }) {
                remove(item)
            }
        }
    }
}

