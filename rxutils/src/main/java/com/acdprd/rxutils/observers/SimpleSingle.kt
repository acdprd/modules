package com.acdprd.rxutils.observers

/**
 * by acdprd | 22.04.2019.
 */

class SimpleSingle<T> @JvmOverloads constructor(onSuccess: (T) -> Unit = {}) :
    LambdaSingle<T>(onSuccess)