package com.av.movie.di

import com.av.movie.data.repository.MovieRepositoryImp
import com.av.movie.data.repository.datasource.MovieRemoteDataSource
import com.av.movie.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMovieRepository(movieRemoteDataSource: MovieRemoteDataSource): MovieRepository {
        return MovieRepositoryImp(movieRemoteDataSource)
    }
}