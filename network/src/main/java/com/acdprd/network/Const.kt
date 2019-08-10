package com.acdprd.network

object Const{

    object Network {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val ACCEPT_LANGUAGE = "Accept-Language"
        const val CLIENT_ABSOLUTE_TIMEOUT = 60   //время ожидания клиента (не ретрофита с RX)
        const val TIMEOUT_GET = 30
        const val TIMEOUT_POST = 30
        const val TIMEOUT_POST_PHOTO = 60
        const val HTTP_CACHE_SIZE = 10   //MB
        const val RETRIES_GET = 2
        const val RETRIES_POST = 0
    }
}