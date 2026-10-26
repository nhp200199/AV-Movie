package com.av.movie.di

import com.av.movie.data.mapper.ActorDTO2Actor
import com.av.movie.data.mapper.CastDTO2Cast
import com.av.movie.data.mapper.CreditDTO2Credit
import com.av.movie.data.mapper.GenreDTO2Genre
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.mapper.MovieDetailDTO2Movie
import com.av.movie.data.mapper.MoviePreviewDTO2Movie
import com.av.movie.data.mapper.VideoDTO2Video
import com.av.movie.data.model.ActorDTO
import com.av.movie.data.model.CastListDTO
import com.av.movie.data.model.CreditsDTO
import com.av.movie.data.model.GenreDTO
import com.av.movie.data.model.MovieDetailDTO
import com.av.movie.data.model.MoviePreviewDTO
import com.av.movie.data.model.VideoListDTO
import com.av.movie.domain.model.Actor
import com.av.movie.domain.model.Cast
import com.av.movie.domain.model.Credit
import com.av.movie.domain.model.Genre
import com.av.movie.domain.model.Movie
import com.av.movie.domain.model.MovieDetail
import com.av.movie.domain.model.Video
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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
    ): Mapper<GenreDTO, List<Genre>>

    @Binds
    abstract fun bindVideoDTO2Video(
        videoDTO2Video: VideoDTO2Video
    ): Mapper<VideoListDTO, List<Video>>

    @Binds
    abstract fun bindActorDTO2Actor(
        actorDTO2Actor: ActorDTO2Actor
    ): Mapper<ActorDTO, Actor>

    @Binds
    abstract fun bindMovieCastDTO2MovieCast(
        mapper: CastDTO2Cast
    ): Mapper<CastListDTO, List<Cast>>

    @Binds
    abstract fun bindCreditDTO2Credit(
        mapper: CreditDTO2Credit
    ): Mapper<CreditsDTO, List<Credit>>

    @Binds
    abstract fun bindMovieDetailDTO2MovieDetail(
        mapper: MovieDetailDTO2Movie
    ): Mapper<MovieDetailDTO, MovieDetail>
}