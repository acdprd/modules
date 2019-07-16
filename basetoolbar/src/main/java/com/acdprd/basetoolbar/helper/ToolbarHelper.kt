package com.acdprd.basetoolbar.helper

abstract class ToolbarHelper {
    abstract var map: MutableMap<Any, Int>

    open fun getDrawableForButton(item: Any, drawableResult: (Int) -> Unit) {
        val d = map[item]
        when {
            d != null -> drawableResult.invoke(d)
        }
    }
}