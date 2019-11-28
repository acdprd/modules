package com.acdprd.rxutils.observers.completable

import io.reactivex.observers.DisposableCompletableObserver

class LambdaDisposableCompletable @JvmOverloads constructor(
    var onComplete: (() -> Unit)? = null,
    var onError: ((Throwable) -> Unit)? = null,
    var disposeIfComplete: Boolean = true
) : DisposableCompletableObserver() {

    override fun onComplete() {
        onComplete?.invoke()
        if (disposeIfComplete) this.dispose()
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        onError?.invoke(e)
    }
}