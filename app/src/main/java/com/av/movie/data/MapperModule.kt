package com.av.movie.data

import com.av.movie.data.credit.CreditDTO2Credit
import com.av.movie.data.model.GenreDTO
import com.av.movie.data.model.MoviePreviewDTO
import com.av.movie.data.movie.MoviePreviewDTO2Movie
import com.av.movie.data.model.Movie
import com.av.movie.data.genre.GenreDTO2Genre
import com.av.movie.data.movie.ActorDTO2Actor
import com.av.movie.data.movie.CastDTO2Cast
import com.av.movie.data.movie.MovieDetailDTO2Movie
import com.av.movie.data.movie.VideoDTO2Video
import com.av.movie.data.model.Actor
import com.av.movie.data.model.ActorDTO
import com.av.movie.data.model.CastListDTO
import com.av.movie.data.model.CreditsDTO
import com.av.movie.data.model.MovieDetail
import com.av.movie.data.model.MovieDetailDTO
import com.av.movie.data.model.VideoListDTO
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
    ): Mapper<GenreDTO, GenreDTO>

    @Binds
    abstract fun bindVideoDTO2Video(
        videoDTO2Video: VideoDTO2Video
    ): Mapper<VideoListDTO, VideoListDTO>

    @Binds
    abstract fun bindActorDTO2Actor(
        actorDTO2Actor: ActorDTO2Actor
    ): Mapper<ActorDTO, Actor>

    @Binds
    abstract fun bindMovieCastDTO2MovieCast(
        mapper: CastDTO2Cast
    ): Mapper<CastListDTO, CastListDTO>

    @Binds
    abstract fun bindCreditDTO2Credit(
        mapper: CreditDTO2Credit
    ): Mapper<CreditsDTO, CreditsDTO>

    @Binds
    abstract fun bindMovieDetailDTO2MovieDetail(
        mapper: MovieDetailDTO2Movie
    ): Mapper<MovieDetailDTO, MovieDetail>
}