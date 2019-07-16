package com.acdprd.adapterandviews.model

import com.acdprd.adapterandviews.model.interfaces.IViewType

enum class BaseViewType : IViewType {
    BASE_TYPE(-1);

    var t: Int = 0

    constructor(t: Int) {
        this.t = t
    }

    constructor() {
        t = this.ordinal
    }

    override fun getType(): Int = ordinal
}