package com.av.movie.presentation.screen.movieDetail

import com.av.movie.core.utils.formatDate
import com.av.movie.dataTest.ALL_GENRES
import com.av.movie.domain.model.Movie
import com.av.movie.domain.model.MovieDetail
import com.av.movie.domain.usecase.FullDetailMovie

private val DEFAULT_GENRE_BY_ID: Map<Int, String> = ALL_GENRES.associate { it.id to it.name }

fun Movie.toPreviewFullDetail(
    genreNameById: Map<Int, String> = DEFAULT_GENRE_BY_ID
): FullDetailMovie {
    val resolvedGenres = genreIds.mapNotNull { genreId ->
        genreNameById[genreId]?.let { name ->
            com.av.movie.domain.model.Genre(id = genreId, name = name)
        }
    }
    return FullDetailMovie(
        movieDetail = MovieDetail(
            adult = adult,
            backdropPath = backdropPath,
            genres = resolvedGenres,
            id = id,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            releaseDate = releaseDate,
            title = title,
            video = video,
            voteAverage = voteAverage,
            voteCount = voteCount,
            duration = 0,
            isFavorite = isFavorite,
            country = ""
        ),
        casts = emptyList(),
        videos = emptyList(),
        recommendations = emptyList()
    )
}

fun formatTrailerPublishedAt(publishedAt: String): String {
    if (publishedAt.isBlank()) return ""
    return formatDate(
        pattern = "dd-MM-yyyy",
        date = publishedAt,
        originPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    ).ifEmpty {
        formatDate(
            pattern = "dd-MM-yyyy",
            date = publishedAt,
            originPattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        )
    }
}
