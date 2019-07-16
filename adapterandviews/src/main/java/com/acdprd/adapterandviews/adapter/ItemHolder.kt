package com.acdprd.adapterandviews.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import com.acdprd.adapterandviews.view.CustomListItemView
import java.lang.IllegalArgumentException

/**
 * Универсальный класс, наследующий от RecyclerView.ViewHolder, для использования в адаптерах списков
 */
class ItemHolder<B : ViewDataBinding, M : Any, V : CustomListItemView<B, Any>?>(var customListItemView: V) :
    RecyclerView.ViewHolder(customListItemView!!) {

    fun getItemView(): V = customListItemView

    fun bindItem(model: M) {
        customListItemView?.setData(model)
    }

    fun setItemClickListener(listener: View.OnClickListener) {
        this.itemView.setOnClickListener(listener)
    }
}
