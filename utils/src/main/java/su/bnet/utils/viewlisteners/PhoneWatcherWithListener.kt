package su.bnet.utils.viewlisteners

import android.text.Editable
import android.widget.EditText
import su.bnet.utils.extensions.onlyDigits
import su.bnet.utils.utils.Utils


class PhoneWatcherWithListener @JvmOverloads constructor(editText: EditText, private val l: (String?, Boolean) -> Unit = { _, _ -> }) : PhonePatternWatcher(editText) {
    var lastState: Boolean = false
        private set

    init {
        editText.addTextChangedListener(this)
    }

    override fun afterTextChanged(s: Editable) {
        super.afterTextChanged(s)
        lastState = Utils.onlyDigits(s.toString()).length >= MAX_LENGTH
        l.invoke(s.toString().onlyDigits(), lastState)
    }
}