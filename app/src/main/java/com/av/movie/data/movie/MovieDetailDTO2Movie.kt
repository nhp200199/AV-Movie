package com.av.movie.data.movie

import com.av.movie.data.Mapper
import com.av.movie.data.model.MovieDetail
import com.av.movie.data.model.MovieDetailDTO
import javax.inject.Inject

class MovieDetailDTO2Movie @Inject constructor(): Mapper<MovieDetailDTO, MovieDetail> {
    override fun map(input: MovieDetailDTO): MovieDetail {
        return MovieDetail(
            adult = input.adult,
            genres = input.genres,
            id = input.id,
            originalTitle = input.originalTitle,
            overview = input.overview ?: "",
            popularity = input.popularity,
            releaseDate = input.releaseDate ?: "",
            title = input.title,
            video = input.video,
            voteAverage = input.voteAverage,
            voteCount = input.voteCount,
            backdropPath = input.backdropPath ?: "",
            originalLanguage = input.originalLanguage,
            posterPath = input.posterPath ?: "",
            duration = input.runtime ?: 0,
            isFavorite = false
        )
    }

}