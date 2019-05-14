package com.acdprd.social.managers

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.util.Log
import com.acdprd.social.model.UserSocialData
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.tasks.Task

/**
 * манагер для работы с гуглом
 */
class GoogleSocialManager(private var serverClientId: String) : BaseSocialManager() {
    private var resultListener: (UserSocialData?) -> Unit = {}
    private lateinit var fragment: Fragment

    companion object {
        const val GOOGLE_AUTH_CODE = 7878
        private val TAG = GoogleSocialManager::class.java.simpleName
    }

    override fun start(fragment: Fragment, l: (UserSocialData?) -> Unit) {
        this.fragment = fragment
        resultListener = l

        Auth.GoogleSignInApi
            .signOut(getClient(fragment.context!!)?.asGoogleApiClient())
            .setResultCallback {
                val client = getClient(fragment.context!!)
                val intent = client?.signInIntent
                fragment.startActivityForResult(intent, GOOGLE_AUTH_CODE)
            }
    }

    override fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GOOGLE_AUTH_CODE) {
            handleSignInResultAndMakeBody(GoogleSignIn.getSignedInAccountFromIntent(data), resultListener)
        }
    }

    private fun getSignInOptions(): GoogleSignInOptions {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(serverClientId)
            .requestEmail()
            .requestProfile()
            .requestId()
            .build()
        return gso
    }

    fun getClient(context: Context): GoogleSignInClient? {
        val client = GoogleSignIn.getClient(context, getSignInOptions())

        return client
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>, accountListener: (GoogleSignInAccount?) -> Unit) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            accountListener.invoke(account)
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode + e.message)
            e.printStackTrace()
            accountListener.invoke(null)
        }
    }

    private fun handleSignInResultAndMakeBody(
        completedTask: Task<GoogleSignInAccount>,
        accountListener: (UserSocialData?) -> Unit
    ) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            handleGoogleSignInAccount(account, accountListener)
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode + e.message)
            e.printStackTrace()
            accountListener.invoke(null)
        }
    }

    private fun handleGoogleSignInAccount(
        lastAccount: GoogleSignInAccount?,
        accountListener: (UserSocialData?) -> Unit
    ) {
        val body = UserSocialData.builder()
            .socialId(lastAccount?.id)
            .name(lastAccount?.givenName)
            .surname(lastAccount?.familyName)
            .email(lastAccount?.email)
            .build()
        accountListener.invoke(body)
    }

    //todo check
    fun logout(context: Context, resultCallback: (Status) -> Unit) {
        Auth.GoogleSignInApi.signOut(getClient(context)?.asGoogleApiClient())
            .setResultCallback { resultCallback.invoke(it) }
    }

    fun checkLastSigned(context: Context, accountListener: (GoogleSignInAccount?) -> Unit) {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        accountListener.invoke(account)
    }

    private fun makeLoginBody(completedTask: Task<GoogleSignInAccount>): GoogleSignInAccount? {
        return completedTask.getResult(ApiException::class.java)
    }

    override fun logout(result: (Boolean) -> Unit) {
        Auth.GoogleSignInApi
            .signOut(getClient(fragment.context!!)?.asGoogleApiClient())
            .setResultCallback {
                result.invoke(it.isSuccess)
            }
    }
}