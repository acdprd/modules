package com.acdprd.social.managers

import android.content.Intent
import android.support.v4.app.Fragment
import com.acdprd.social.model.UserSocialData

abstract class BaseSocialManager {
    abstract fun start(fragment: Fragment, l: (UserSocialData?) -> Unit)
    abstract fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    abstract fun logout(result: (Boolean) -> Unit = {})
}