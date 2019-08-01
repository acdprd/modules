package su.bnet.utils.viewlisteners

import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.widget.EditText

/**
 * класс помогает с едитТекстами, которые надо проверять на валидность, и за которыми надо наблюдать
 */

class EditTextChecker(@ColorRes val normalColor: Int, @ColorRes val wrongColor: Int) {
    private var map = HashMap<EditText, (EditText) -> Boolean>()
    private var changeListener: () -> Unit = {}
    private var textWatcher = LambdaTextWatcher { changeListener.invoke() }
    private var changeBackground: Boolean = false
    @DrawableRes
    private var normalDrawable: Int = 0
    @DrawableRes
    private var wrongDrawable: Int = 0

    fun withChangeBackground(@DrawableRes normal: Int, @DrawableRes wrong: Int): EditTextChecker {
        changeBackground = true
        normalDrawable = normal
        wrongDrawable = wrong

        return this
    }

    fun withSomeThingChangeListener(l: () -> Unit): EditTextChecker {
        changeListener = l
        return this
    }

    fun add(et: EditText, checker: (EditText) -> Boolean) {
        et.setOnFocusChangeListener { v, hasFocus -> updateState(v as EditText, hasFocus, checker) }
        et.addTextChangedListener(textWatcher)
        map[et] = checker
    }

    fun check(et: EditText) = if (map.containsKey(et)) map[et]!!.invoke(et) else false

    private fun updateState(v: EditText, f: Boolean, checker: (EditText) -> Boolean) {
        if (f)
            viewCheck(v, true)
        else {
            viewCheck(v, checker.invoke(v))
        }
    }

    private fun viewCheck(et: EditText, normal: Boolean) {
        if (normal) {
            if (changeBackground) et.background = et.context.resources.getDrawable(normalDrawable)
            et.setTextColor(et.context.resources.getColor(normalColor))
        } else {
            if (changeBackground) et.background = et.context.resources.getDrawable(wrongDrawable)
            et.setTextColor(et.context.resources.getColor(wrongColor))
        }
        changeListener.invoke()
    }
}