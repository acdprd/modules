package com.acdprd.modules.view

import android.content.Context
import android.util.AttributeSet
import com.acdprd.adapterandviews.view.CustomListItemView
import com.acdprd.modules.model.ModelS
import com.acdprd.modules.R
import com.acdprd.modules.databinding.ViewCustomSBinding

class CustomSView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) :  CustomListItemView<ViewCustomSBinding, ModelS>(context, attrs, defStyleAttr) {

    init {
        setMatchWrap()
    }

    override fun setData(model: ModelS) {
        binding.tvS.text = model.text
    }

    override fun getLayoutRes(): Int = R.layout.view_custom_s

}