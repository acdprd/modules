package com.acdprd.modules.adapter

import android.content.Context
import com.acdprd.adapterandviews.view.CustomListItemView
import com.acdprd.modules.model.ViewType2
import com.acdprd.modules.view.CustomFView
import com.acdprd.modules.view.CustomSView

class TestAdapter2 : ItemAdapter2() {
    override fun createCustomView(
        context: Context,
        viewType: ViewType2?
    ): CustomListItemView<*, *>? {
        return when (viewType) {
            ViewType2.FIRST2 -> CustomFView(context)
            ViewType2.SECOND2 -> CustomSView(context)

            else -> null
        }
    }
}