package com.av.movie.di

import com.av.movie.data.repository.datasource.ActorRemoteDataSource
import com.av.movie.data.repository.datasource.CastRemoteDataSource
import com.av.movie.data.repository.datasource.CreditRemoteDataSource
import com.av.movie.data.repository.datasource.GenreRemoteDataSource
import com.av.movie.data.repository.datasource.MovieDetailRemoteDataSource
import com.av.movie.data.repository.datasource.MoviePreviewRemoteDataSource
import com.av.movie.data.repository.datasource.VideoRemoteDataSource
import com.av.movie.data.repository.datasourceimp.ActorRemoteDataSourceImp
import com.av.movie.data.repository.datasourceimp.CastRemoteDataSourceImp
import com.av.movie.data.repository.datasourceimp.CreditRemoteDataSourceImp
import com.av.movie.data.repository.datasourceimp.GenreRemoteDataSourceImp
import com.av.movie.data.repository.datasourceimp.MovieDetailRemoteDataSourceImp
import com.av.movie.data.repository.datasourceimp.MoviePreviewRemoteDataSourceImp
import com.av.movie.data.repository.datasourceimp.VideoRemoteDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataModule {

    @Binds
    abstract fun bindMoviePreviewDataSource(
        moviePreviewRemoteDataSource: MoviePreviewRemoteDataSourceImp
    ): MoviePreviewRemoteDataSource

    @Binds
    abstract fun bindGenreDataSource(
        genreDataSource: GenreRemoteDataSourceImp
    ): GenreRemoteDataSource

    @Binds
    abstract fun bindCreditDataSource(
        creditDataSource: CreditRemoteDataSourceImp
    ): CreditRemoteDataSource

    @Binds
    abstract fun bindMovieCastDataSource(
        movieCastDataSource: CastRemoteDataSourceImp
    ): CastRemoteDataSource

    @Binds
    abstract fun bindActorDataSource(
        actorDataSource: ActorRemoteDataSourceImp
    ): ActorRemoteDataSource

    @Binds
    abstract fun bindVideoDataSource(
        videoDataSource: VideoRemoteDataSourceImp
    ): VideoRemoteDataSource

    @Binds
    abstract fun bindMovieDetailDataSource(
        movieDetailDataSource: MovieDetailRemoteDataSourceImp
    ): MovieDetailRemoteDataSource
}