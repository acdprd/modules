package com.acdprd.basetoolbar.model

import android.content.Context
import android.graphics.drawable.Drawable

interface IToolbarHelper<TB : Enum<TB>> {
    fun getDrawableForButton(toolbarButton: TB): Int
}