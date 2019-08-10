package com.acdprd.adapterandviews.helper

/**
 * by acdprd | 24.05.2019.
 */

class Paginator {
    private var loading: Boolean = false
    private var nextPageListener: (Int) -> Unit = {}
    private var lastItemPredicate: (Int) -> Boolean = { false }
    private var itemCountChecker: () -> Int = { 0 }

    fun loading() {
        loading = true
    }

    fun loadingOff() {
        loading = false
    }

    fun setNextpageListener(l: (Int) -> Unit): Paginator {
        nextPageListener = l
        return this
    }

    fun setItemsCountChecker(l: () -> Int): Paginator {
        itemCountChecker = l
        return this
    }

    fun setLastItemPredicate(l: (Int) -> Boolean): Paginator {
        lastItemPredicate = l
        return this
    }

    fun check(pos:Int) {
        if (lastItemPredicate.invoke(pos) && !loading) {
            loading()
            nextPageListener.invoke(itemCountChecker.invoke())
        }
    }
}