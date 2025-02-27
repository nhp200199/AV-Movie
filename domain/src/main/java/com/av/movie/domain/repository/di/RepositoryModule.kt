package com.av.movie.domain.repository.di

import com.av.movie.domain.repository.genre.GenreRepository
import com.av.movie.domain.repository.genre.IGenreRepository
import com.av.movie.domain.repository.movie.IMoviePreviewRepository
import com.av.movie.domain.repository.movie.MoviePreviewRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMovieListRepository(
        repository: MoviePreviewRepository
    ): IMoviePreviewRepository

    @Binds
    abstract fun bindGenreRepository(
        repository: GenreRepository
    ): IGenreRepository
}