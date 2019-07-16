package com.acdprd.basetoolbar.toolbar

import android.content.Context
import android.util.AttributeSet
import com.acdprd.adapterandviews.view.CustomListItemView
import com.acdprd.basetoolbar.R
import com.acdprd.basetoolbar.model.IAction

class ToolbarOptionItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomListItemView<ViewToolbarOptionItemBinding, IAction>(context, attrs, defStyleAttr) {

    init {
        setWrapWrap()
    }

    override fun setData(model: IAction) {
        binding.tvOptionItemTitle.setText(ContentActionHelper.getTextForAction(model))
        binding.tvOptionItemTitle.setCompoundDrawablesWithIntrinsicBounds(ContentActionHelper.getIconResource(model), 0, 0, 0)
    }

    override fun getLayoutRes(): Int = R.layout.view_toolbar_option_item

}