package com.av.movie.data.movie

import com.av.movie.data.model.MoviePreviewDTO
import com.av.movie.data.Mapper
import com.av.movie.data.model.Movie
import javax.inject.Inject

class MoviePreviewDTO2Movie @Inject constructor() : Mapper<MoviePreviewDTO, Movie> {
    override fun map(input: MoviePreviewDTO): Movie {
        return Movie(
            adult = input.adult,
            backdropPath = input.backdropPath ?: "",
            genreIds = input.genreIds,
            id = input.id,
            originalLanguage = input.originalLanguage,
            originalTitle = input.originalTitle,
            overview = input.overview,
            popularity = input.popularity,
            posterPath = input.posterPath ?: "",
            title = input.title,
            releaseDate = input.releaseDate,
            video = input.video,
            voteAverage = input.voteAverage,
            voteCount = input.voteCount
        )
    }
}