package com.acdprd.network

import com.acdprd.network.exceptions.ApiError
import com.acdprd.network.exceptions.ApiErrorException
import com.acdprd.network.exceptions.EmptyException
import com.acdprd.network.helper.ErrorBodyConverter
import com.acdprd.network.model.UserTokenInfo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

fun <T : Any> Observable<T>.setup(timeout: Int, retries: Int): Observable<T> {
    return this.compose { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .timeout(timeout.toLong(), TimeUnit.SECONDS)
            .retry(retries.toLong())
    }
}

fun <T : Any> Single<T>.setup(timeout: Int, retries: Int): Single<T> {
    return this.compose { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .timeout(timeout.toLong(), TimeUnit.SECONDS)
            .retry(retries.toLong())
    }
}

fun Completable.setup(timeout: Int, retries: Int): Completable {
    return this.compose { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .timeout(timeout.toLong(), TimeUnit.SECONDS)
            .retry(retries.toLong())
    }
}

fun Completable.setupGet(timeout: Int = Const.Network.TIMEOUT_GET, retries: Int = Const.Network.RETRIES_GET): Completable = this.setup(timeout, retries)

fun Completable.setupPost(timeout: Int = Const.Network.TIMEOUT_POST, retries: Int = Const.Network.RETRIES_POST): Completable = this.setup(timeout, retries)

fun <T : Any> Observable<T>.setupGet(timeout: Int = Const.Network.TIMEOUT_GET, retries: Int = Const.Network.RETRIES_GET): Observable<T> = this.setup(timeout, retries)

fun <T : Any> Observable<T>.setupPost(timeout: Int = Const.Network.TIMEOUT_POST, retries: Int = Const.Network.RETRIES_POST): Observable<T> = this.setup(timeout, retries)

fun <T : Any> Single<T>.setupGet(timeout: Int = Const.Network.TIMEOUT_GET, retries: Int = Const.Network.RETRIES_GET): Single<T> = this.setup(timeout, retries)

fun <T : Any> Single<T>.setupPost(timeout: Int = Const.Network.TIMEOUT_POST, retries: Int = Const.Network.RETRIES_POST): Single<T> = this.setup(timeout, retries)

fun <T : Any> Observable<T>.checkUnAuthorized(uti: UserTokenInfo): Observable<T> {
    return if(uti.token == null) {
        Observable.empty<T>()
    } else this
}

fun <T : Any> Single<T>.checkUnAuthorized(uti: UserTokenInfo): Single<T> {
    return if(uti.token == null) {
       Single.never() //todo?
    } else this
}

fun Completable.checkUnAuthorized(uti: UserTokenInfo): Completable {
    return if(uti.token == null) {
        Completable.complete()
    } else this
}

fun <T : Any> Observable<Response<T>>.responseData(errorConverter:ErrorBodyConverter<ApiError>): Observable<T> {
    return this.map { it.responseData(errorConverter) }
}

fun <T : Any> Single<Response<T>>.responseData(errorConverter:ErrorBodyConverter<ApiError>): Single<T> {
    return this.map { it.responseData(errorConverter) }
}

@Throws(IOException::class)
private fun <R : Any> Response<R>.responseData(errorConverter:ErrorBodyConverter<ApiError>): R {
    return if (this.errorBody() == null && this.body() != null) this.body()!!
    else if (this.errorBody() != null /*&& this.body() == null*/) throw ApiErrorException(
       errorConverter.getErrorBodyAs(this)
    )
    else if (!(this.body() is Unit) && this.body() == null) throw EmptyException()
    else this.body()!!
}