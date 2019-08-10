package com.acdprd.network

import android.content.Context

import java.io.File

class CacheFileCreator {
    fun generateCacheFile(context: Context): File {
        val file = File(context.cacheDir, "HttpCache")
        file.mkdirs()
        return file
    }
}
