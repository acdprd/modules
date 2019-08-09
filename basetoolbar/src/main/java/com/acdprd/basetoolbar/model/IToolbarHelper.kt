package com.acdprd.basetoolbar.model

import android.graphics.drawable.Drawable

interface IToolbarHelper<TB : Enum<TB>> {
    fun getDrawableForButton(toolbarButton: TB): Drawable?
}