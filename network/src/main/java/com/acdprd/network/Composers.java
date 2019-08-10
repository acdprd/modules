package com.acdprd.network;

import java.util.concurrent.TimeUnit;

import io.reactivex.CompletableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.schedulers.Schedulers;
/**
 * Created by Timur Khakimov on 12.04.2019
 * В отдельный класс выносим composer-ы для установления каких-то параметров для запросов к АПИ
 */
public class Composers {

    public static  class Observable{
        public static <T> ObservableTransformer<T, T> setupGet() {
            return setup(Const.Network.TIMEOUT_GET, Const.Network.RETRIES_GET);
        }

        public static <T> ObservableTransformer<T, T> setupPost() {
            return setup(Const.Network.TIMEOUT_POST, Const.Network.RETRIES_POST);
        }

        private static <T> ObservableTransformer<T, T> setup(final int timeout, final int retries) {
            return upstream -> upstream
                    .subscribeOn(Schedulers.io())
                    .timeout(timeout, TimeUnit.SECONDS)
                    .retry(retries);
        }
    }

    public static class Single{
        public static <T> SingleTransformer<T, T> setupGet() {
            return setup(Const.Network.TIMEOUT_GET, Const.Network.RETRIES_GET);
        }

        public static <T> SingleTransformer<T, T> setupPost() {
            return setup(Const.Network.TIMEOUT_POST, Const.Network.RETRIES_POST);
        }

        private static <T> SingleTransformer<T, T> setup(final int timeout, final int retries) {
            return upstream -> upstream
                    .subscribeOn(Schedulers.io())
                    .timeout(timeout, TimeUnit.SECONDS)
                    .retry(retries);
        }
    }

    public static class Completable{
        public static CompletableTransformer setupGet() {
            return setup(Const.Network.TIMEOUT_GET, Const.Network.RETRIES_GET);
        }

        public static CompletableTransformer setupPost() {
            return setup(Const.Network.TIMEOUT_POST, Const.Network.RETRIES_POST);
        }

        private static CompletableTransformer setup(final int timeout, final int retries) {
            return upstream -> upstream
                    .subscribeOn(Schedulers.io())
                    .timeout(timeout, TimeUnit.SECONDS)
                    .retry(retries);
        }
    }
}
