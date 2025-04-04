package com.av.movie.domain.repository.di

import com.av.movie.domain.repository.actor.ActorRepository
import com.av.movie.domain.repository.actor.IActorRepository
import com.av.movie.domain.repository.credit.CreditRepository
import com.av.movie.domain.repository.credit.ICreditRepository
import com.av.movie.domain.repository.genre.GenreRepository
import com.av.movie.domain.repository.genre.IGenreRepository
import com.av.movie.domain.repository.movie.IMovieDetailRepository
import com.av.movie.domain.repository.movie.IMoviePreviewRepository
import com.av.movie.domain.repository.movie.MovieDetailRepository
import com.av.movie.domain.repository.movie.MoviePreviewRepository
import com.av.movie.domain.repository.movie.cast.IMovieCastRepository
import com.av.movie.domain.repository.movie.cast.MovieCastRepository
import com.av.movie.domain.repository.movie.video.IVideoRepository
import com.av.movie.domain.repository.movie.video.VideoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieListRepository(
        repository: MoviePreviewRepository
    ): IMoviePreviewRepository

    @Binds
    abstract fun bindGenreRepository(
        repository: GenreRepository
    ): IGenreRepository

    @Binds
    abstract fun bindActorRepository(
        repository: ActorRepository
    ): IActorRepository

    @Binds
    abstract fun bindMovieCastRepository(
        repository: MovieCastRepository
    ): IMovieCastRepository

    @Binds
    abstract fun bindCreditRepository(
        repository: CreditRepository
    ): ICreditRepository

    @Binds
    abstract fun bindVideoRepository(
        repository: VideoRepository
    ): IVideoRepository

    @Binds
    abstract fun bindMovieDetailRepository(
        repository: MovieDetailRepository
    ): IMovieDetailRepository
}