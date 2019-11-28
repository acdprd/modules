package com.acdprd.modules.test

import com.acdprd.rxutils.asObservable
import com.acdprd.rxutils.asSingle
import com.acdprd.rxutils.observers.single.LambdaSingle
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import su.bnet.utils.helper.Logg
import kotlin.random.Random

object MoveTest {
    var id: Int = 0

    fun test() {
        log("start")
        val a = System.currentTimeMillis()
        val moves: List<Move> = Work.generateMoves(10)
//        log(moves.asJson())
        val works = mutableListOf<Single<Result>>()

        //добавляем все в один список
        moves.forEach { move ->
            works.add(
                asSingle { e ->
                    val result = Work.getMoveResult(move)
                    e.onSuccess(result)
                }
            )
        }

        //мерджим
        Single.mergeDelayError(works)
            .subscribeOn(Schedulers.newThread())
            .reduce( //собираем результаты в один список
                mutableListOf<Result>(),
                { listResult, result ->
                    listResult.add(result)
                    listResult
                })
            .map { results -> Work.getBestResults(results) } //находим наилучший
//            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                LambdaSingle(
                    {
                        val b = System.currentTimeMillis()
                        logThread("final result ${b - a}")
                        log(it?.value.toString())
                    },
                    { it.printStackTrace() })
            )
    }

    fun test2() {
        log("start")
        val a = System.currentTimeMillis()
        val moves: List<Move> = Work.generateMoves(10)
//        log(moves.asJson())
        val works = mutableListOf<Observable<Result>>()

        //добавляем все в один список
        moves.forEach { move ->
            works.add(
                asObservable { e ->
                    val result = Work.getMoveResult(move)
                    e.onNext(result)
                    e.onComplete()
                }
            )
        }

        //мерджим
        Observable.mergeDelayError(works,10)
        .reduce( //собираем результаты в один список
                mutableListOf<Result>(),
                { listResult, result ->
                    listResult.add(result)
                    listResult
                })
            .map { results -> Work.getBestResults(results) } //находим наилучший
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                LambdaSingle(
                    {
                        val b = System.currentTimeMillis()
                        logThread("final result ${b - a}")
                        log(it?.value.toString())
                    },
                    { it.printStackTrace() })
            )


    }

    object Work {
        fun getMoveResult(move: Move): Result {
            logThread("getMoveResult")
            move.result.doHardWork()
            return move.result
        }

        fun getBestResults(results: List<Result>): Result? {
            logThread("getBestResults")
            return results.maxBy { it.value }
        }

        fun generateMoves(count: Int): List<Move> {
            val res = mutableListOf<Move>()
            for (i in 0 until count) {
                res.add(Move(Result(Random.nextInt(10000000))))
            }
            return res
        }
    }

    fun logThread(where: String) {
        Logg.i(where, Thread.currentThread().name.toString())
    }

    fun log(what: String) {
        Logg.v("MOVETEST", what)
    }
}

class Result(var value: Int) {
    fun doHardWork() {
        HardWork(++MoveTest.id)
    }
}

class Move(val result: Result)






