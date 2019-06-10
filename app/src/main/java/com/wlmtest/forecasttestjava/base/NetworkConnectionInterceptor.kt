package com.wlmtest.forecasttestjava.base

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

import java.io.IOException

abstract class NetworkConnectionInterceptor : Interceptor {

    abstract val isInternetAvailable: Boolean

    abstract fun onInternetUnavailable()

    abstract fun onCacheUnavailable()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!isInternetAvailable) {
            onInternetUnavailable()
            request = request.newBuilder().header(
                "Cache-Control",
                "public, only-if-cached, max-stale=" + 60 * 60 * 24
            ).build()
            val response = chain.proceed(request)
            if (response.cacheResponse() == null) {
                onCacheUnavailable()
            }
            return response
        }
        return chain.proceed(request)
    }
}