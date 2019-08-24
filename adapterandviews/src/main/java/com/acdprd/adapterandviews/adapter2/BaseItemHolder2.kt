package com.acdprd.adapterandviews.adapter2

import android.support.v7.widget.RecyclerView
import android.view.View

open class BaseItemHolder2<V : View>(view: V?) : RecyclerView.ViewHolder(view!!) {

    open fun setItemClickListener(listener: View.OnClickListener) {
        this.itemView.setOnClickListener(listener)
    }

    protected open fun getItemView(): V {
        return itemView as  V
    }
}