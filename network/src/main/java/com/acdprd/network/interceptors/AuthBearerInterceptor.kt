package com.acdprd.network.interceptors

import com.acdprd.network.Const
import com.acdprd.network.model.UserTokenInfo

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class AuthBearerInterceptor(private val userTokenInfo: UserTokenInfo) : Interceptor {
    private val token: String?
        get() = userTokenInfo.token

    private val tokenWithBearer: String
        get() = " Bearer " + userTokenInfo.token!!

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        if (token != null)
            builder.addHeader(Const.Network.AUTHORIZATION_HEADER, tokenWithBearer)
        return chain.proceed(builder.build())
    }
}

