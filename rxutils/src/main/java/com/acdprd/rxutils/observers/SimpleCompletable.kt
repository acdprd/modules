package com.acdprd.rxutils.observers

/**
 * by acdprd | 22.04.2019.
 */

class SimpleCompletable @JvmOverloads constructor(onComplete: () -> Unit = {}) : LambdaCompletable(onComplete)