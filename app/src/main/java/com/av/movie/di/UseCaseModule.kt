package com.av.movie.di

import com.av.movie.domain.repository.MovieRepository
import com.av.movie.domain.usecase.GetLatestMovieUseCase
import com.av.movie.domain.usecase.GetMovieUseCase
import com.av.movie.domain.usecase.MovieUseCase
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