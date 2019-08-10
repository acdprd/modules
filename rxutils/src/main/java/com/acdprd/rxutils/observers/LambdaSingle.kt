package com.acdprd.rxutils.observers

import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

/**
 * by acdprd | 22.04.2019.
 */

open class LambdaSingle<T> @JvmOverloads constructor(
    var onSuccess: (T) -> Unit = {},
    var onError: (Throwable) -> Unit = {},
    var onSubscribe: (Disposable) -> Unit = {}
) : SingleObserver<T> {

    override fun onSuccess(t: T) {
        onSuccess.invoke(t)
    }

    override fun onSubscribe(d: Disposable) {
        onSubscribe.invoke(d)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        onError.invoke(e)
    }
}