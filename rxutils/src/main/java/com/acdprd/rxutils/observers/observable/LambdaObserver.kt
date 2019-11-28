package com.acdprd.rxutils.observers.observable

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * сабскрабер на лямбдах
 */
open class LambdaObserver<T> @JvmOverloads constructor(
        var onNext: (T) -> Unit = {},
        var onError: (Throwable) -> Unit = {},
        var onComplete: () -> Unit = {},
        var onSubscribe: (Disposable) -> Unit = {}
) : Observer<T> {

    override fun onComplete() {
        onComplete.invoke()
    }

    override fun onSubscribe(d: Disposable) {
        onSubscribe.invoke(d)
    }

    override fun onNext(t: T) {
        onNext.invoke(t)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        onError.invoke(e)
    }
}