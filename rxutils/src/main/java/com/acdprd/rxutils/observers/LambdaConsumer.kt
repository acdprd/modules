package com.acdprd.rxutils.observers

import io.reactivex.functions.Consumer

/**
 * by acdprd | 23.04.2019.
 */

class LambdaConsumer<T : Any> @JvmOverloads constructor(var accept: (T) -> Unit = {}) : Consumer<T> {
    override fun accept(t: T) {
        accept.invoke(t)
    }
}