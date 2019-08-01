package su.bnet.utils.extensions

import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import su.bnet.smartpot.Consts
import su.bnet.smartpot.network.*
import su.bnet.smartpot.network.exceptions.ApiError
import su.bnet.smartpot.network.exceptions.ApiErrorException
import su.bnet.smartpot.network.exceptions.EmptyException
import su.bnet.smartpot.network.model.requests.Empty
import java.io.IOException

/**
 * by acdprd | 22.04.2019.
 */

fun asCompletable(work: (CompletableEmitter) -> Unit): Completable {
    return Completable.create { e -> work.invoke(e) }
}

fun <T : Any> asObservable(work: (ObservableEmitter<T>) -> Unit): Observable<T> {
    return Observable.create { e -> work.invoke(e) }
}

fun <T : Any?> asSingle(t: T): Single<T> {
    return Single.create { e -> e.onSuccess(t) }
}

fun <T : Any> asSingle(work: (SingleEmitter<T>) -> Unit): Single<T> {
    return Single.create { e -> work.invoke(e) }
}

private fun <T : Any> Observable<T>.setup(timeout: Int, retries: Int): Observable<T> {
    return this.compose { upstream ->
        upstream.subscribeOn(Schedulers.io())
                .timeout(timeout.toLong(), TimeUnit.SECONDS)
                .retry(retries.toLong())
    }
}

private fun <T : Any> Single<T>.setup(timeout: Int, retries: Int): Single<T> {
    return this.compose { upstream ->
        upstream.subscribeOn(Schedulers.io())
                .timeout(timeout.toLong(), TimeUnit.SECONDS)
                .retry(retries.toLong())
    }
}

private fun Completable.setup(timeout: Int, retries: Int): Completable {
    return this.compose { upstream ->
        upstream.subscribeOn(Schedulers.io())
                .timeout(timeout.toLong(), TimeUnit.SECONDS)
                .retry(retries.toLong())
    }
}

fun Completable.setupGet(timeout: Int = Consts.Network.TIMEOUT_GET, retries: Int = Consts.Network.RETRIES_GET): Completable = this.setup(timeout, retries)

fun Completable.setupPost(timeout: Int = Consts.Network.TIMEOUT_POST, retries: Int = Consts.Network.RETRIES_POST): Completable = this.setup(timeout, retries)

fun <T : Any> Observable<T>.setupGet(timeout: Int = Consts.Network.TIMEOUT_GET, retries: Int = Consts.Network.RETRIES_GET): Observable<T> = this.setup(timeout, retries)

fun <T : Any> Observable<T>.setupPost(timeout: Int = Consts.Network.TIMEOUT_POST, retries: Int = Consts.Network.RETRIES_POST): Observable<T> = this.setup(timeout, retries)

fun <T : Any> Single<T>.setupGet(timeout: Int = Consts.Network.TIMEOUT_GET, retries: Int = Consts.Network.RETRIES_GET): Single<T> = this.setup(timeout, retries)

fun <T : Any> Single<T>.setupPost(timeout: Int = Consts.Network.TIMEOUT_POST, retries: Int = Consts.Network.RETRIES_POST): Single<T> = this.setup(timeout, retries)

fun <T : Any> Observable<T>.noOpUnAuthorized(uti: UserTokenInfo): Observable<T> {
    return if(uti.token == null) {
        log("unauthorized, noop")
        Observable.empty<T>()
    } else this
}

fun Completable.noOpUnAuthorized(uti: UserTokenInfo): Completable {
    return if(uti.token == null) {
        log("unauthorized, noop")
        Completable.complete()
    } else this
}

fun <T : Any> Observable<Response<T>>.responseData(): Observable<T> {
    return this.map { it.responseData() }
}

fun Observable<Response<Empty>>.emptyData(): Observable<Empty> {
    return this.map { it.emptyData() }
}

fun <T : Any> Single<Response<T>>.responseData(): Single<T> {
    return this.map { it.responseData() }
}

fun Single<Response<Empty>>.emptyData(): Single<Empty> {
    return this.map { it.emptyData() }
}

fun Response<Empty>.emptyData(): Empty? {
    return if (this.errorBody() == null) {
        Empty()
    } else {
        throw ApiErrorException(getErrorBodyAs(this, ApiError::class.java)
        )
    }
}

@Throws(IOException::class)
private fun <R : Any> Response<R>.responseData(): R {
    return if (this.errorBody() == null && this.body() != null) this.body()!!
    else if (this.errorBody() != null /*&& this.body() == null*/) throw ApiErrorException(getErrorBodyAs(this, ApiError::class.java))
    else if (!(this.body() is Unit) && this.body() == null) throw EmptyException()
    else this.body()!!
}

@Throws(IOException::class)
fun <T> getErrorBodyAs(response: Response<*>?, type: Class<T>): T {
    if (response?.errorBody() == null) {
        throw IOException("no body in response")
    }
    // TODO: 12.04.2019 подумать, откуда и как лучше получать объект Retrofit
    val converter = Api.instance.retrofit.responseBodyConverter<T>(type, arrayOfNulls(0))
    return converter.convert(response.errorBody()!!)
}