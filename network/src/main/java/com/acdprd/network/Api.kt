package com.acdprd.network

import com.acdprd.network.helper.ApiErrorConverter
import com.acdprd.network.test.TestApi
import com.acdprd.network.test.TestService
import retrofit2.Retrofit

class Api {
    lateinit var retrofit: Retrofit
    lateinit var errorConverter: ApiErrorConverter

    lateinit var testApi: TestApi
        private set

    fun test() {
        retrofit = Retrofit.Builder().build()
        errorConverter = ApiErrorConverter(retrofit)

        testApi = TestApi(retrofit.create(TestService::class.java), errorConverter)
    }
}