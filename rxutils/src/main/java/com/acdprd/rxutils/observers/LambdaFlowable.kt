package com.acdprd.rxutils.observers

import io.reactivex.FlowableSubscriber
import io.reactivex.disposables.Disposable
import org.reactivestreams.Subscription

/**
 * by acdprd | 21.08.2019.
 */

open class LambdaFlowable<T : Any> @JvmOverloads constructor(
        var onNext: (T) -> Unit = {},
        var onError: (Throwable) -> Unit = {},
        var onComplete: () -> Unit = {},
        var onSubscribe: (Subscription) -> Unit = {}) : FlowableSubscriber<T> {

    override fun onComplete() {
       onComplete.invoke()
    }

    override fun onSubscribe(s: Subscription) {
        onSubscribe.invoke(s)
    }

    override fun onNext(t: T) {
        onNext.invoke(t)
    }

    override fun onError(t: Throwable) {
        t.printStackTrace()
        onError.invoke(t)
    }

}