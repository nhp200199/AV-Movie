package com.av.movie.di

import com.av.movie.oldClass.MovieApi
import com.av.movie.oldClass.MovieRemoteDataSource
import com.av.movie.oldClass.MovieRemoteDataSourceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {
    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(movieApi: MovieApi): MovieRemoteDataSource {
        return MovieRemoteDataSourceImp(movieApi)
    }
}