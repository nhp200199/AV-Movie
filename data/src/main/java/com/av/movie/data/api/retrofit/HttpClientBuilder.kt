package com.av.movie.data.api.retrofit

import android.content.Context
import com.av.movie.data.networkManager.NetworkManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class HttpClientBuilder {
    companion object {
        fun default(context: Context): OkHttpClient.Builder {
            val networkMonitorInterceptor = NetworkMonitorInterceptor(NetworkManager(context))

            val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            builder.addInterceptor(logging)
            builder.addInterceptor(networkMonitorInterceptor)

            return builder
        }
    }
}