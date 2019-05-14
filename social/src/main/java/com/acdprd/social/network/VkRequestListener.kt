package com.acdprd.social.network

import android.util.Log
import com.vk.sdk.api.VKError
import com.vk.sdk.api.VKRequest
import com.vk.sdk.api.VKResponse

class VkRequestListener @JvmOverloads constructor(
    var onComplete: (response: VKResponse) -> Unit,
    var onError: (VKError?) -> Unit = {}
) : VKRequest.VKRequestListener() {
    override fun onComplete(response: VKResponse?) {
        super.onComplete(response)
        response?.let { r ->
            onComplete.invoke(r)
        } ?: let {
            onError.invoke(VKError(RESPONSE_IS_NULL))
        }

    }

    override fun onError(error: VKError?) {
        super.onError(error)
        Log.e(TAG, error?.errorMessage)
        onError.invoke(error)
    }

    companion object {
        private val TAG = VkRequestListener::class.java.simpleName
        private const val RESPONSE_IS_NULL = 1213
    }
}