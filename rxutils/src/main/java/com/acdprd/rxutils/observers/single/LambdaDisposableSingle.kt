package com.acdprd.rxutils.observers.single

import io.reactivex.observers.DisposableSingleObserver

class LambdaDisposableSingle<T> @JvmOverloads constructor(
    var onSuccess: ((T) -> Unit)? = null,
    var onError: ((Throwable) -> Unit)? = null,
    var disposeIfSuccess: Boolean = true
) : DisposableSingleObserver<T>() {

    override fun onSuccess(t: T) {
        onSuccess?.invoke(t)
        if (disposeIfSuccess) this.dispose()
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        onError?.invoke(e)
    }
}