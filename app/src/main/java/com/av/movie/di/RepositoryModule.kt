package com.av.movie.di

import com.av.movie.data.repository.actor.ActorRepositoryImp
import com.av.movie.data.repository.credit.CreditRepositoryImp
import com.av.movie.data.repository.genre.GenreRepositoryImp
import com.av.movie.data.repository.movie.MovieDetailRepositoryImp
import com.av.movie.data.repository.movie.MoviePreviewRepositoryImp
import com.av.movie.data.repository.movie.cast.MovieCastRepositoryImp
import com.av.movie.data.repository.movie.video.VideoRepositoryImp
import com.av.movie.domain.repository.actor.ActorRepository
import com.av.movie.domain.repository.credit.CreditRepository
import com.av.movie.domain.repository.genre.GenreRepository
import com.av.movie.domain.repository.movie.MovieDetailRepository
import com.av.movie.domain.repository.movie.MoviePreviewRepository
import com.av.movie.domain.repository.movie.cast.MovieCastRepository
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
        repository: MoviePreviewRepositoryImp
    ): MoviePreviewRepository

    @Binds
    abstract fun bindGenreRepository(
        repository: GenreRepositoryImp
    ): GenreRepository

    @Binds
    abstract fun bindActorRepository(
        repository: ActorRepositoryImp
    ): ActorRepository

    @Binds
    abstract fun bindMovieCastRepository(
        repository: MovieCastRepositoryImp
    ): MovieCastRepository

    @Binds
    abstract fun bindCreditRepository(
        repository: CreditRepositoryImp
    ): CreditRepository

    @Binds
    abstract fun bindVideoRepository(
        repository: VideoRepositoryImp
    ): VideoRepository

    @Binds
    abstract fun bindMovieDetailRepository(
        repository: MovieDetailRepositoryImp
    ): MovieDetailRepository
}