package com.acdprd.network

import com.acdprd.network.interceptors.AuthBearerInterceptor
import com.acdprd.network.interceptors.HttpCurlInterceptor
import com.acdprd.network.model.UserTokenInfo
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by Timur Khakimov on 12.04.2019
 * Класс для создания объекта Retrofit и всех сопустствующих
 * Принимает на вход объект File для кеша и UserTokenInfo для хранения информации о токене
 */
class RetrofitCreator constructor(
    var baseUrl: String,
    var interceptors: List<Interceptor> = listOf()
) {

    //=====
    private val gsonConverterFactory: GsonConverterFactory
        get() = GsonConverterFactory.create(GsonBuilder().create())
    private val rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
        get() = RxJava2CallAdapterFactory.create()
    private val httpCurlInterceptor: HttpCurlInterceptor
        get() = HttpCurlInterceptor()
    private val httpLoggingInterceptor: HttpLoggingInterceptor
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            return interceptor
        }

    fun generateRetrofit(cacheFile: File, userTokenInfo: UserTokenInfo): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .client(getOkHttpClient(cacheFile, userTokenInfo))
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    private fun getOkHttpClient(cacheFile: File, userTokenInfo: UserTokenInfo): OkHttpClient {
        val builder = OkHttpClient()
            .newBuilder()
            .writeTimeout(Const.Network.CLIENT_ABSOLUTE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(Const.Network.CLIENT_ABSOLUTE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .connectTimeout(Const.Network.CLIENT_ABSOLUTE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(getAuthInterceptor(userTokenInfo))
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(httpCurlInterceptor)
        for (interceptor in interceptors){
            builder.addInterceptor(interceptor)
        }

        return builder
            .cache(getCache(cacheFile))
            .build()
    }

    private fun getCache(cacheFile: File): Cache {
        return Cache(cacheFile, (Const.Network.HTTP_CACHE_SIZE * 1024 * 1024).toLong())
    }

    private fun getAuthInterceptor(userTokenInfo: UserTokenInfo): AuthBearerInterceptor {
        return AuthBearerInterceptor(userTokenInfo)
    }
}
