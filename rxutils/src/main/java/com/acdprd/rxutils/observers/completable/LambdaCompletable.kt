package com.acdprd.rxutils.observers.completable

import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable

/**
 * by acdprd | 22.04.2019.
 */

open class LambdaCompletable @JvmOverloads constructor(
        var onComplete: () -> Unit = {},
        var onError: (Throwable) -> Unit = {},
        var onSubscribe: (Disposable) -> Unit = {}) : CompletableObserver {

    override fun onComplete() {
        onComplete.invoke()
    }

    override fun onSubscribe(d: Disposable) {
        onSubscribe.invoke(d)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        onError.invoke(e)
    }

}