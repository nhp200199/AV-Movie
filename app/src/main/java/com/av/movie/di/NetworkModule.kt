package com.av.movie.di

import android.content.Context
import com.av.movie.core.network.HttpClientBuilder
import com.av.movie.core.network.NetworkAdapterFactory
import com.av.movie.data.api.ActorApi
import com.av.movie.data.api.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun retrofit(@ApplicationContext context: Context): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/") //TODO: Use Build Config instead
        .client(HttpClientBuilder.default(context).build())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(NetworkAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun movieApi(retrofit: Retrofit): MovieApi =
        retrofit.create(MovieApi::class.java)

    @Provides
    @Singleton
    fun actorApi(retrofit: Retrofit): ActorApi =
        retrofit.create(ActorApi::class.java)
}