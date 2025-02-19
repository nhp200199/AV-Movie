package com.av.movie.di

import com.av.movie.oldClass.MovieRepositoryImp
import com.av.movie.oldClass.MovieRemoteDataSource
import com.av.movie.oldClass.MovieRepository
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