package com.acdprd.rxutils

import io.reactivex.*

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

