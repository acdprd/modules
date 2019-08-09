package com.acdprd.modules.view

import android.content.Context
import android.util.AttributeSet
import com.acdprd.adapterandviews.view.CustomListItemView
import com.acdprd.modules.R
import com.acdprd.modules.databinding.ViewOptionBinding
import com.acdprd.modules.model.action.Action

class OptionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomListItemView<ViewOptionBinding, Action>(context, attrs, defStyleAttr) {


    override fun setData(model: Action) {
        binding.tvActionTitle.text = model.name
    }

    override fun getLayoutRes(): Int = R.layout.view_option

}