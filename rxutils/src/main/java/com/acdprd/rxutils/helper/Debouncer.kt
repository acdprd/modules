package com.acdprd.rxutils.helper

import io.reactivex.Emitter
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.observers.LambdaObserver
import java.util.concurrent.TimeUnit

class Debouncer<T : Any>(var onNext: (T) -> Unit) {
    private lateinit var obsString: Observable<T>
    private var disposable: Disposable? = null
    private var emitter: Emitter<T>? = null

    init {
        makeObs()
        subscribeObs()
    }

    private fun subscribeObs() {
        obsString.subscribe(LambdaObserver(
                { onNext.invoke(it) },
                {},
                {},
                { disposable = it }))
    }

    private fun makeObs() {
        obsString = Observable.create(
                ObservableOnSubscribe<T> { emitter ->
                    this.emitter = emitter
                })
                .debounce(DEBOUNCE_DEFAULT, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun clear() {
        emitter = null
    }

    fun update(onNext: (T) -> Unit) {
        this.onNext = onNext
        disposable?.dispose()
        makeObs()
        subscribeObs()
    }

    fun post(item: T) {
        emitter?.onNext(item)
    }

    companion object{
        const val DEBOUNCE_DEFAULT = 500L
    }
}