package com.acdprd.network.test

import com.acdprd.network.exceptions.ApiError
import com.acdprd.network.helper.ErrorBodyConverter
import com.acdprd.network.responseData
import com.acdprd.network.setupGet
import io.reactivex.Single

class TestApi (var service:TestService,var errorConverter: ErrorBodyConverter<ApiError>){

    fun test():Single<Boolean> = service.test().setupGet().responseData(errorConverter)
}