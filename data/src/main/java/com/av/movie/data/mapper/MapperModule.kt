package com.av.movie.data.mapper

import com.av.movie.data.model.Genre
import com.av.movie.data.model.GenreDTO
import com.av.movie.data.model.MoviePreviewDTO
import com.av.movie.data.mapper.movie.MoviePreviewDTO2Movie
import com.av.movie.data.model.Movie
import com.av.movie.data.mapper.genre.GenreDTO2Genre
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    @Binds
    abstract fun bindMoviePreviewDTO2Movie(
        moviePreviewDTO2Movie: MoviePreviewDTO2Movie
    ): Mapper<MoviePreviewDTO, Movie>

    @Binds
    abstract fun bindGenreDTO2Genre(
        genreDTO2Genre: GenreDTO2Genre
    ): Mapper<GenreDTO, GenreDTO>
}