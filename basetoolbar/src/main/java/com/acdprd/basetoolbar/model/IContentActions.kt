package com.acdprd.basetoolbar.model

interface IContentActions<ACTION:Enum<ACTION>> {
    var actions:MutableList<ACTION>
}