package com.av.movie.di

import android.content.Context
import com.av.movie.core.network.HttpClientBuilder
import com.av.movie.core.network.NetworkAdapterFactory
import com.av.movie.data.api.ActorApi
import com.av.movie.data.api.CastApi
import com.av.movie.data.api.CreditApi
import com.av.movie.data.api.GenreApi
import com.av.movie.data.api.MovieDetailApi
import com.av.movie.data.api.MoviePreviewApi
import com.av.movie.data.api.VideoApi
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
    fun moviePreviewApi(retrofit: Retrofit): MoviePreviewApi =
        retrofit.create(MoviePreviewApi::class.java)

    @Provides
    @Singleton
    fun genreApi(retrofit: Retrofit): GenreApi =
        retrofit.create(GenreApi::class.java)

    @Provides
    @Singleton
    fun creditApi(retrofit: Retrofit): CreditApi =
        retrofit.create(CreditApi::class.java)

    @Provides
    @Singleton
    fun actorApi(retrofit: Retrofit): ActorApi =
        retrofit.create(ActorApi::class.java)

    @Provides
    @Singleton
    fun videoApi(retrofit: Retrofit): VideoApi =
        retrofit.create(VideoApi::class.java)

    @Provides
    @Singleton
    fun movieDetailApi(retrofit: Retrofit): MovieDetailApi =
        retrofit.create(MovieDetailApi::class.java)

    @Provides
    @Singleton
    fun castApi(retrofit: Retrofit): CastApi =
        retrofit.create(CastApi::class.java)
}