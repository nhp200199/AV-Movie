package com.av.movie.di

import com.av.movie.data.api.MovieApi
import com.av.movie.data.repository.datasource.MovieRemoteDataSource
import com.av.movie.data.repository.datasourceimp.MovieRemoteDataSourceImp
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