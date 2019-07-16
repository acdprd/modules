package com.acdprd.basetoolbar.toolbar

import android.content.Context
import android.util.AttributeSet
import com.acdprd.adapterandviews.view.CustomListItemView
import com.acdprd.basetoolbar.model.IContentActions

class ToolbarOptionsView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomListItemView<ViewToolbarOptionsBinding, IContentActions>(context, attrs, defStyleAttr) {
    private var actionListener: (ContentAction) -> Unit = {}

    init {
        setWrapWrap()
    }

    override fun setData(model: IContentActions) {
        for (m in model.actions) {
            val v = ToolbarOptionItemView(context)
            v.setOnClickListener { actionListener.invoke(m) }
            v.setData(m)
            binding.llOptions.addView(v)
        }
    }

    override fun getLayoutRes(): Int = R.layout.view_toolbar_options

    fun setActionListener(l: (ContentAction) -> Unit) {
        actionListener = l
    }
}