package com.av.movie.data.di

import com.av.movie.data.datasource.remote.actor.ActorRemoteDataSource
import com.av.movie.data.datasource.remote.actor.IActorRemoteDataSource
import com.av.movie.data.datasource.remote.credit.CreditRemoteDataSource
import com.av.movie.data.datasource.remote.credit.ICreditRemoteDataSource
import com.av.movie.data.model.GenreDTO
import com.av.movie.data.model.Movie
import com.av.movie.data.model.MoviePreviewDTO
import com.av.movie.data.datasource.remote.genre.GenreRemoteDataSource
import com.av.movie.data.datasource.remote.genre.IGenreRemoteDataSource
import com.av.movie.data.datasource.remote.movie.IMovieDetailRemoteDataSource
import com.av.movie.data.datasource.remote.movie.IMoviePreviewRemoteDataSource
import com.av.movie.data.datasource.remote.movie.MovieDetailRemoteDataSource
import com.av.movie.data.datasource.remote.movie.MoviePreviewRemoteDataSource
import com.av.movie.data.datasource.remote.movie.cast.CastRemoteDataSource
import com.av.movie.data.datasource.remote.movie.cast.ICastRemoteDataSource
import com.av.movie.data.datasource.remote.movie.video.IVideoRemoteDataSource
import com.av.movie.data.datasource.remote.movie.video.VideoRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindMoviePreviewDataSource(
        moviePreviewRemoteDataSource: MoviePreviewRemoteDataSource
    ): IMoviePreviewRemoteDataSource<MoviePreviewDTO, Movie>

    @Binds
    abstract fun bindGenreDataSource(
        genreDataSource: GenreRemoteDataSource
    ): IGenreRemoteDataSource<GenreDTO, GenreDTO>

    @Binds
    abstract fun bindCreditDataSource(
        creditDataSource: CreditRemoteDataSource
    ): ICreditRemoteDataSource

    @Binds
    abstract fun bindMovieCastDataSource(
        movieCastDataSource: CastRemoteDataSource
    ): ICastRemoteDataSource

    @Binds
    abstract fun bindActorDataSource(
        actorDataSource: ActorRemoteDataSource
    ): IActorRemoteDataSource

    @Binds
    abstract fun bindVideoDataSource(
        videoDataSource: VideoRemoteDataSource
    ): IVideoRemoteDataSource

    @Binds
    abstract fun bindMovieDetailDataSource(
        movieDetailDataSource: MovieDetailRemoteDataSource
    ): IMovieDetailRemoteDataSource
}