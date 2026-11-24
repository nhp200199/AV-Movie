package com.av.movie.presentation.screen.movieDetail

import com.av.movie.domain.model.Genre
import com.av.movie.domain.model.Movie
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MovieDetailHelpersTest {

    @Test
    fun toPreviewFullDetail_populatesAvailableFieldsAndResolvesGenres() {
        val movie = Movie(
            adult = false,
            backdropPath = "/backdrop.jpg",
            genreIds = listOf(28, 878, 999999),
            id = 76600,
            originalLanguage = "en",
            originalTitle = "Avatar: The Way of Water",
            overview = "Jake Sully lives with his newfound family.",
            popularity = 900.0,
            posterPath = "/poster.jpg",
            releaseDate = "2022-12-14",
            title = "Avatar: The Way of Water",
            video = false,
            voteAverage = 9.8,
            voteCount = 5000,
            isFavorite = true
        )

        val preview = movie.toPreviewFullDetail()
        val detail = preview.movieDetail

        assertEquals(76600, detail.id)
        assertEquals("Avatar: The Way of Water", detail.title)
        assertEquals("/poster.jpg", detail.posterPath)
        assertEquals("/backdrop.jpg", detail.backdropPath)
        assertEquals("Jake Sully lives with his newfound family.", detail.overview)
        assertEquals(9.8, detail.voteAverage, 0.001)
        assertEquals("2022-12-14", detail.releaseDate)
        assertEquals("en", detail.originalLanguage)
        assertTrue(detail.isFavorite)
        assertEquals(
            listOf(Genre(28, "Action"), Genre(878, "Science Fiction")),
            detail.genres
        )
        assertTrue(preview.casts.isEmpty())
        assertTrue(preview.videos.isEmpty())
        assertTrue(preview.recommendations.isEmpty())
    }

    @Test
    fun formatTrailerPublishedAt_formatsIsoTimestampsWithAndWithoutMillis() {
        assertEquals("31-12-2024", formatTrailerPublishedAt("2024-12-31T15:59:55.000Z"))
        assertEquals("29-09-2021", formatTrailerPublishedAt("2021-09-29T13:00:05Z"))
        assertEquals("", formatTrailerPublishedAt(""))
        assertEquals("", formatTrailerPublishedAt("invalid-date"))
    }
}
