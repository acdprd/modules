package com.acdprd.rxutils.observers.completable

import com.acdprd.rxutils.observers.completable.LambdaCompletable

/**
 * by acdprd | 22.04.2019.
 */

class SimpleCompletable @JvmOverloads constructor(onComplete: () -> Unit = {}) : LambdaCompletable(onComplete)