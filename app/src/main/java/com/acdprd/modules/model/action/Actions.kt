package com.acdprd.modules.model.action

import com.acdprd.basetoolbar.model.IContentActions

enum class Action{
    ACTION_1,
    ACTION_2
}

class ContentActions(var acts:MutableList<Action>) : IContentActions<Action>{
    override var actions: MutableList<Action>
        get() = acts
        set(value) {}

}