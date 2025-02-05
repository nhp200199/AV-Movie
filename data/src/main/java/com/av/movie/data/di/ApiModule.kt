package com.av.movie.data.di

import android.content.Context
import com.av.movie.data.api.retrofit.HttpClientBuilder
import com.av.movie.data.api.retrofit.NetworkAdapterFactory
import com.av.movie.data.api.retrofit.service.MovieListService
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
object ApiModule {
    @Provides
    @Singleton
    fun retrofit(@ApplicationContext context: Context): Retrofit = Retrofit.Builder()
        .baseUrl("https://moviesdatabase.p.rapidapi.com")
        .client(HttpClientBuilder.default(context).build())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(NetworkAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun movieListService(retrofit: Retrofit): MovieListService =
        retrofit.create(MovieListService::class.java)
}