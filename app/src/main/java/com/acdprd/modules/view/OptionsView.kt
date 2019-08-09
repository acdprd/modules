package com.acdprd.modules.view

import android.content.Context
import android.util.AttributeSet
import com.acdprd.adapterandviews.view.CustomListItemView
import com.acdprd.basetoolbar.model.IOptionsView
import com.acdprd.modules.R
import com.acdprd.modules.databinding.ViewOptionsBinding
import com.acdprd.modules.model.action.Action
import com.acdprd.modules.model.action.ContentActions

class OptionsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomListItemView<ViewOptionsBinding, ContentActions>(context, attrs, defStyleAttr),IOptionsView<ContentActions,Action> {
    private var listener:(Action)->Unit = {}

    override fun setActionListener(l: (Action) -> Unit) {
        listener = l
    }

    override fun setData(model: ContentActions) {
        binding.llOpts.removeAllViews()
        model.actions.forEach { action ->
            val v = OptionView(context).also {
                it.setData(action)
                it.setOnClickListener { listener.invoke(action) }
            }
            binding.llOpts.addView(v)
        }
    }

    override fun getLayoutRes(): Int = R.layout.view_options

}