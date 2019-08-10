package com.acdprd.network.helper

import com.acdprd.network.exceptions.ApiError
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

open class ErrorBodyConverter<T : Any>(retrofit: Retrofit, type: Class<T>) {
    private val errorConverter: Converter<ResponseBody, T> =
        retrofit.responseBodyConverter(type, arrayOfNulls(0))

    @Throws(IOException::class)
    fun getErrorBodyAs(response: Response<*>?): T {
        if (response?.errorBody() == null) {
            throw IOException("no body in response")
        }
        return errorConverter.convert(response.errorBody()!!)
    }
}

class ApiErrorConverter(retrofit: Retrofit) :
    ErrorBodyConverter<ApiError>(retrofit, ApiError::class.java)

