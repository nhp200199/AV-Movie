package com.av.movie.data.mapper

import com.av.movie.data.api.model.MoviePreviewDTO
import com.av.movie.data.mapper.movie.MoviePreviewDTO2Movie
import com.av.movie.domain.model.Movie
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    @Binds
    @Singleton
    abstract fun bindMoviePreviewDTO2Movie(
        moviePreviewDTO2Movie: MoviePreviewDTO2Movie
    ): Mapper<MoviePreviewDTO, Movie>
}