package com.av.movie.di

import com.av.movie.oldClass.MovieRepository
import com.av.movie.oldClass.GetLatestMovieUseCase
import com.av.movie.oldClass.GetMovieUseCase
import com.av.movie.oldClass.MovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideMovieUseCase(movieRepository: MovieRepository): MovieUseCase {
        return MovieUseCase(
            getMovieUseCase = GetMovieUseCase(movieRepository),
            getLatestMovieUseCase = GetLatestMovieUseCase(movieRepository)
        )
    }
}