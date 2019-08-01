package su.bnet.utils.viewlisteners

import android.text.Editable
import android.widget.EditText

/**
 * by acdprd | 17.04.2019.
 */

class LambdaAutoTextWatcher(et: EditText, after: (Editable) -> Unit) : LambdaTextWatcher(after) {
    init {
        et.addTextChangedListener(this)
    }
}