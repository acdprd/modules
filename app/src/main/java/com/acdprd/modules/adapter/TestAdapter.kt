package com.acdprd.modules.adapter

import android.content.Context
import android.util.Log
import android.view.View
import com.acdprd.modules.model.ViewType
import com.acdprd.modules.view.CustomFView
import com.acdprd.modules.view.CustomSView
import java.util.*

class TestAdapter : ItemAdapter() {
    override fun getCustomView(context: Context, viewType: ViewType?): View? {
        Log.d("TAG", viewType.toString())
        return when (viewType) {
            ViewType.SECOND -> CustomSView(context)
            ViewType.FIRST -> CustomFView(context)
            else -> null
        }
    }
}