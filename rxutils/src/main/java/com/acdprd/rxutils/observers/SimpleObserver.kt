package com.acdprd.rxutils.observers

/**
 * by acdprd | 17.05.2019.
 */

class SimpleObserver<T> (onNext:(T)->Unit) : LambdaObserver<T>(onNext)