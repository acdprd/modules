package com.acdprd.adapterandviews.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

abstract class BasePagerAdapter<T:Any> (var items:MutableList<T>) : PagerAdapter(){

    abstract fun makeView(context: Context,item:T):View

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val v = makeView(container.context,items[position])
        container.addView(v)
        return v
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun getCount(): Int = items.size

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }
}