package com.av.movie.data.retrofit

import com.av.movie.data.exception.NoNetworkConnectionException
import com.av.movie.data.networkManager.INetworkManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class NetworkMonitorInterceptor(private val networkManager: INetworkManager): Interceptor  {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkManager.isNetworkConnected()) throw NoNetworkConnectionException()

        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}