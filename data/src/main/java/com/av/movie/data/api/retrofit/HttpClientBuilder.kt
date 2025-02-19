package com.av.movie.data.api.retrofit

import android.content.Context
import com.av.movie.data.networkManager.NetworkManager
import okhttp3.Interceptor
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
            builder.addInterceptor(Interceptor { chain ->
                val newRequestBuilder = chain.request().newBuilder()
                newRequestBuilder.addHeader("Authentication", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5NTJjZDU3OTFiYzhmMzE3ZDk2ZWVjZmYxMTU2NTNmMSIsIm5iZiI6MTczNDM0NjkwMi4xMzc5OTk4LCJzdWIiOiI2NzYwMDg5NjVlM2FiZDY4MDZiYjAwMDMiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.MWBjWyMTFLKAEuuZVRpfpep2k9_nWseCH1Ni-dYOqr4")
                chain.proceed(newRequestBuilder.build())
            })

            return builder
        }
    }
}