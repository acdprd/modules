package su.bnet.utils.viewlisteners

import android.widget.EditText

/**
 * by acdprd | 17.04.2019.
 */

class LambdaAutoPhoneWatcher(et:EditText) : PhonePatternWatcher(et){
    init {
        et.addTextChangedListener(this)
    }
}