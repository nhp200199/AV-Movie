package com.av.movie.presentation.navigation

import com.av.movie.domain.model.Movie
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class MovieNavPayloadTest {

    @Test
    fun movieNavPayload_roundTripsAllFieldsIncludingSpecialCharacters() {
        val original = Movie(
            adult = false,
            backdropPath = "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg",
            genreIds = listOf(28, 12, 878),
            id = 76600,
            originalLanguage = "en",
            originalTitle = "Avatar: The Way of Water?",
            overview = "100% action & drama / \"quoted\" line\nwith newline + #hash",
            popularity = 1423.75,
            posterPath = "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg",
            releaseDate = "2022-12-14",
            title = "Avatar: Dòng Chảy Của Nước?",
            video = false,
            voteAverage = 9.8,
            voteCount = 11543,
            isFavorite = true
        )

        val encoded = encodeMovieNavPayload(original.toNavPayload())
        assertFalse(encoded.contains('/'))
        assertFalse(encoded.contains('?'))
        assertFalse(encoded.contains('&'))
        assertFalse(encoded.contains('#'))
        assertFalse(encoded.contains('%'))
        assertFalse(encoded.contains('='))

        val decoded = decodeMovieNavPayload(encoded).toDomain()
        assertEquals(original, decoded)
    }
}
