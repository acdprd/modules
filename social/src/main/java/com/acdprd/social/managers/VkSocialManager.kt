package com.acdprd.social.managers

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.acdprd.social.model.UserSocialData
import com.acdprd.social.network.VkRequestListener
import com.acdprd.social.network.model.VkInfo
import com.acdprd.social.network.model.VkResponse
import com.google.gson.Gson
import com.vk.sdk.*
import com.vk.sdk.api.*
import com.vk.sdk.dialogs.VKShareDialogBuilder
import java.util.*

/**
 * манагер для работы с вк
 */

open class VkSocialManager : BaseSocialManager() {
    private var vkToken: VKAccessToken? = VKAccessToken.currentToken()
    private var vkCallback: VKCallback<VKAccessToken>
    private var resultListener: (UserSocialData?) -> Unit = {}

    init {
        vkCallback = object : VKCallback<VKAccessToken> {
            override fun onResult(res: VKAccessToken) {
                vkToken = res
                tryProfileRequestVkApi()
            }

            override fun onError(error: VKError?) {
                println(error?.errorMessage)
            }
        }
    }

    override fun start(fragment: Fragment, l: (UserSocialData?) -> Unit) {
        this.resultListener = l

        if (isAuth()) tryProfileRequestVkApi()
        else fragment.startActivityForResult(
            getIntentToLogin(fragment.context!!),
            VKServiceActivity.VKServiceType.Authorization.outerCode
        )
    }

    override fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        VKSdk.onActivityResult(requestCode, resultCode, data, vkCallback)
    }

    override fun logout(result: (Boolean) -> Unit) {
        VKSdk.logout()
        result.invoke(VKAccessToken.currentToken() == null)
    }

    private fun tryProfileRequestVkApi() {
        val request: VKRequest = VKApi.users().get(VKParameters.from(VKApiConst.USER_ID, vkToken?.userId))
        request.executeWithListener(VkRequestListener({ response ->
            //{"nameValuePairs":{"response":{"values":[{"nameValuePairs":{"id":1111,"first_name":"aaa","last_name":"aaa"}}]}}} //todo wip
            resultListener.invoke(makeLoginBodyFromJson(response.responseString, vkToken?.userId))
        }))
    }

    protected open fun makeLoginBodyFromJson(json: String, userId: String?): UserSocialData {
        val answer: VkResponse = Gson().fromJson(json, VkResponse::class.java)
        var firstAnswer: VkInfo? = null
        if (answer.values != null) {
            if (answer.values!!.isNotEmpty())
                firstAnswer = answer.values!![0]
        }
        val res = UserSocialData.builder()
            .email(firstAnswer?.email)
            .name(firstAnswer?.firstName)
            .surname(firstAnswer?.lastName)
            .socialId(userId)
            .build()

        return res
    }

    private fun isAuth(): Boolean = VKAccessToken.currentToken() != null

    fun tryShare(
        fragment: Fragment,
        shareText: String?,
        linkTitle: String?,
        shareLink: String?,
        shareDialog: (VKShareDialogBuilder) -> Unit
    ) {
        setVkCallback { tryShare(fragment, shareText, linkTitle, shareLink, shareDialog) }
        if (isAuth()) {
            shareDialog.invoke(shareWithLink(shareText, linkTitle, shareLink))
        } else {
            fragment.startActivityForResult(
                getIntentToLogin(fragment.context!!),
                VKServiceActivity.VKServiceType.Authorization.outerCode
            )
        }
    }

    private fun setVkCallback(onResult: () -> Unit) {
        vkCallback = object : VKCallback<VKAccessToken> {
            override fun onResult(res: VKAccessToken) {
                vkToken = res
                onResult.invoke()
            }

            override fun onError(error: VKError?) {
                println(error?.errorMessage)
            }
        }
    }

    private fun shareWithLink(shareText: String?, linkTitle: String?, shareLink: String?): VKShareDialogBuilder {
        val builder = VKShareDialogBuilder()
        builder.setText(shareText)
        builder.setAttachmentLink(linkTitle, shareLink)

        return builder
    }

    companion object {
        fun getIntentToLogin(context: Context): Intent {
            val intent =
                Intent(context, VKServiceActivity::class.java)//todo https://github.com/VKCOM/vk-android-sdk/issues/170
            intent.putExtra("arg1", "Authorization")
            val scopes = ArrayList<String>()

            scopes.add(VKScope.OFFLINE)
            scopes.add(VKScope.EMAIL)
            scopes.add(VKScope.WALL)

            intent.putStringArrayListExtra("arg2", scopes)
            intent.putExtra("arg4", VKSdk.isCustomInitialize())
            return intent
        }
    }
}