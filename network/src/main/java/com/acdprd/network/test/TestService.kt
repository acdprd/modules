package com.acdprd.network.test

import io.reactivex.Single
import retrofit2.Response

interface TestService {
    fun test():Single<Response<Boolean>>
}