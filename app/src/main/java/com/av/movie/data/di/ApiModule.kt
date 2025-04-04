package com.av.movie.data.di

import android.content.Context
import com.av.movie.data.retrofit.HttpClientBuilder
import com.av.movie.data.retrofit.NetworkAdapterFactory
import com.av.movie.data.retrofit.service.GenreService
import com.av.movie.data.retrofit.service.IActorService
import com.av.movie.data.retrofit.service.ICastService
import com.av.movie.data.retrofit.service.ICreditService
import com.av.movie.data.retrofit.service.IMovieDetailService
import com.av.movie.data.retrofit.service.IVideoService
import com.av.movie.data.retrofit.service.MoviePreviewService
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
        .baseUrl("https://api.themoviedb.org/3/") //TODO: Use Build Config instead
        .client(HttpClientBuilder.default(context).build())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(NetworkAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun moviePreviewService(retrofit: Retrofit): MoviePreviewService =
        retrofit.create(MoviePreviewService::class.java)

    @Provides
    @Singleton
    fun genreService(retrofit: Retrofit): GenreService =
        retrofit.create(GenreService::class.java)

    @Provides
    @Singleton
    fun creditService(retrofit: Retrofit): ICreditService =
        retrofit.create(ICreditService::class.java)

    @Provides
    @Singleton
    fun actorService(retrofit: Retrofit): IActorService =
        retrofit.create(IActorService::class.java)

    @Provides
    @Singleton
    fun videoService(retrofit: Retrofit): IVideoService =
        retrofit.create(IVideoService::class.java)

    @Provides
    @Singleton
    fun movieDetailService(retrofit: Retrofit): IMovieDetailService =
        retrofit.create(IMovieDetailService::class.java)

    @Provides
    @Singleton
    fun castService(retrofit: Retrofit): ICastService =
        retrofit.create(ICastService::class.java)
}