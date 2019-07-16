package com.acdprd.modules.view

import android.content.Context
import android.util.AttributeSet
import com.acdprd.adapterandviews.view.CustomListItemView
import com.acdprd.modules.model.ModelF
import com.acdprd.modules.R
import com.acdprd.modules.databinding.ViewCustomFBinding

class CustomFView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomListItemView<ViewCustomFBinding, ModelF>(context, attrs, defStyleAttr) {

    init {
        setMatchWrap()
    }

    override fun setData(model: ModelF) {
        binding.tvF.text = model.text
    }

    override fun getLayoutRes(): Int = R.layout.view_custom_f

}