package su.bnet.utils.viewlisteners

import android.text.Editable
import android.text.TextWatcher

/**
 * текстВотчер на лямбдах
 */
open class LambdaTextWatcher(var after: (Editable) -> Unit) : TextWatcher {
    private var before: (CharSequence) -> Unit = {}
    private var onText: (CharSequence) -> Unit = {}

    constructor(after: (Editable) -> Unit, before: (CharSequence) -> Unit, onText: (CharSequence) -> Unit) : this(after) {
        this.before = before
        this.onText = onText
    }

    override fun afterTextChanged(s: Editable) {
        after.invoke(s)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        before.invoke(s)
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        onText.invoke(s)
    }

}