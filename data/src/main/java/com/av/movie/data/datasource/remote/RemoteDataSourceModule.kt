package com.av.movie.data.datasource.remote

import com.av.movie.data.api.model.GenreDTO
import com.av.movie.data.api.model.Movie
import com.av.movie.data.api.model.MoviePreviewDTO
import com.av.movie.data.datasource.remote.genre.GenreRemoteDataSource
import com.av.movie.data.datasource.remote.genre.IGenreRemoteDataSource
import com.av.movie.data.datasource.remote.movie.IMovieListRemoteDataSource
import com.av.movie.data.datasource.remote.movie.MoviePreviewRemoteDataSource
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
    ): IMovieListRemoteDataSource<MoviePreviewDTO, Movie>

    @Binds
    abstract fun bindGenreDataSource(
        genreDataSource: GenreRemoteDataSource
    ): IGenreRemoteDataSource<GenreDTO, GenreDTO>
}