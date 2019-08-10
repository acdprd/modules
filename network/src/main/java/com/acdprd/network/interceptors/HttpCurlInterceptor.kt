package com.acdprd.network.interceptors

import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.internal.platform.Platform
import okio.Buffer

/**
 * Created by Timur Khakimov on 12.04.2019
 * логирование запросов в виде curl
 */
class HttpCurlInterceptor @JvmOverloads constructor(private val logger: Logger = Logger.DEFAULT) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val curlH = StringBuilder()
        val url = request.url().toString()
        val method: String
        var data = ""
        val HEADER = " -H \"%s\" "
        val METHOD = " -X %s "
        val DATA = " --data \"%s\" "

        val requestBody = request.body()
        val hasRequestBody = requestBody != null
        method = String.format(METHOD, request.method())

        if (hasRequestBody) {
            if (requestBody!!.contentType() != null) {
                curlH.append(String.format(HEADER, "Content-Type: " + requestBody.contentType()!!))
            }

            if (requestBody.contentLength() != -1L) {
                curlH.append(
                    String.format(
                        HEADER,
                        "Content-Length: " + requestBody.contentLength()
                    )
                )
            }
        }

        val headers = request.headers()
        var i = 0

        val count = headers.size()
        while (i < count) {
            val name = headers.name(i)
            if (!"Content-Type".equals(name, ignoreCase = true) && !"Content-Length".equals(
                    name,
                    ignoreCase = true
                )
            ) {
                curlH.append(String.format(HEADER, name + ": " + headers.value(i)))
            }
            ++i
        }

        if (hasRequestBody) {
            if (!this.bodyEncoded(request.headers())) {
                val buffer = Buffer()
                requestBody!!.writeTo(buffer)
                var charset: Charset? = UTF8
                val contentType = requestBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }

                //this.logger.log("");
                if (isPlaintext(buffer)) {
                    data = String.format(DATA, buffer.readString(charset!!))
                }
            }
        }

        this.logger.log("--> HTTP CURL")
        val s = "curl $method$curlH$data $url"
        this.logger.log(s)
        this.logger.log("--> HTTP CURL END")

        val response: Response
        try {
            response = chain.proceed(request)
        } catch (var24: Exception) {
            throw var24
        }

        return response
    }

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers.get("Content-Encoding")
        return contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
    }

    interface Logger {

        fun log(message: String)

        companion object {
            val DEFAULT: Logger = object : Logger {
                override fun log(message: String) {
                    Platform.get().log(4, message, null as Throwable?)
                }
            }
        }
    }

    companion object {

        private val UTF8 = Charset.forName("UTF-8")

        internal fun isPlaintext(buffer: Buffer): Boolean {
            try {
                val prefix = Buffer()
                val byteCount = if (buffer.size() < 64L) buffer.size() else 64L
                buffer.copyTo(prefix, 0L, byteCount)

                var i = 0
                while (i < 16 && !prefix.exhausted()) {
                    val codePoint = prefix.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false
                    }
                    ++i
                }

                return true
            } catch (var6: EOFException) {
                return false
            }

        }
    }
}
