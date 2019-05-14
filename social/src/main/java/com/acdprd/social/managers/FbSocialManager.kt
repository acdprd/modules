package com.acdprd.social.managers

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import com.acdprd.social.model.UserSocialData
import com.facebook.*
import com.facebook.internal.CallbackManagerImpl
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.share.Sharer
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import org.json.JSONException
import com.acdprd.social.managers.FbSocialManager.Consts.EMAIL
import com.acdprd.social.managers.FbSocialManager.Consts.FIELDS
import com.acdprd.social.managers.FbSocialManager.Consts.FIRST_NAME
import com.acdprd.social.managers.FbSocialManager.Consts.ID
import com.acdprd.social.managers.FbSocialManager.Consts.LAST_NAME
import com.acdprd.social.managers.FbSocialManager.Consts.NAME
import com.acdprd.social.managers.FbSocialManager.Consts.PUBLIC_PROFILE
import java.util.*

/**
 * манагер для работы с фб
 */
open class FbSocialManager : BaseSocialManager() {


    object Consts {
        const val PUBLIC_PROFILE = "public_profile"
        const val EMAIL = "email"
        const val NAME = "name"
        const val FIELDS = "fields"
        const val ID = "id"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val LINK = "link"
    }

    private var accessToken: AccessToken? = AccessToken.getCurrentAccessToken()
    private var callbackManager: CallbackManager = CallbackManager.Factory.create()
    private var resultListener: (UserSocialData?) -> Unit = {}
    private lateinit var fragment: Fragment

    override fun start(fragment: Fragment, l: (UserSocialData?) -> Unit) {
        resultListener = l
        this.fragment = fragment
        tryLogin()
        tryProfileRequest()
    }

    override fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
        if (requestCode == CallbackManagerImpl.RequestCodeOffset.Share.toRequestCode()) {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun logout(result: (Boolean) -> Unit) {
        LoginManager.getInstance().logOut()
    }

    private fun tryProfileRequest() {
        accessToken = AccessToken.getCurrentAccessToken()
        if (accessToken != null) {
            getFacebookUserProfile()
        } else {
            LoginManager.getInstance().logInWithReadPermissions(fragment, Arrays.asList(PUBLIC_PROFILE, EMAIL))
        }
    }

    private fun getFacebookUserProfile() {
        var id: String? = null
        var mail: String? = null
        var name: String? = null
        var firstName: String? = null
        var lastName: String? = null

        val request = GraphRequest.newMeRequest(accessToken) { `object`, response ->
            try {
                id = `object`.getString(ID)
                name = `object`.getString(NAME)
                mail = `object`.optString(EMAIL)
                firstName = `object`.optString(FIRST_NAME)
                lastName = `object`.optString(LAST_NAME)
            } catch (e: JSONException) {
            }
            val body = UserSocialData.builder()
                    .email(mail)
                    .name(firstName)
                    .surname(lastName)
                    .socialId(id)
                    .build()
            resultListener.invoke(body)
        }

        val parameters = Bundle()
        parameters.putString(FIELDS, arrayListOf(ID, NAME, FIRST_NAME, LAST_NAME, EMAIL).joinToString(separator = ", "))
        request.parameters = parameters
        request.executeAsync()
    }

    private fun tryLogin() {
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                accessToken = result.accessToken
                tryProfileRequest()
//                    onResult(result)
            }

            override fun onCancel() {
                //todo
            }

            override fun onError(error: FacebookException) {
                //todo
                error.printStackTrace()
            }
        })
    }

    @JvmOverloads
    fun share(linkTo: String?, quote: String? = null, frg: Fragment, callback: FacebookCallback<Sharer.Result>) {
        val shareDialog = ShareDialog(frg)
        shareDialog.show(getShareContent(linkTo, quote))
        shareDialog.registerCallback(callbackManager, callback)
    }

    private fun getShareContent(url: String?, quote: String? = null): ShareLinkContent {
        return ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(url))
                .build()
    }

}