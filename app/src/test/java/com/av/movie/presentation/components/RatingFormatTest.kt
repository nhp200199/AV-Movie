package com.av.movie.presentation.components

import org.junit.Assert.assertEquals
import org.junit.Test

class RatingFormatTest {

    @Test
    fun formatMovieRating_roundsToOneDecimalPlace() {
        assertEquals("6.8", formatMovieRating(6.778))
        assertEquals("7.3", formatMovieRating(7.327))
        assertEquals("9.8", formatMovieRating(9.8))
        assertEquals("7.0", formatMovieRating(7.0))
    }

    @Test
    fun formatMovieRating_returnsPlaceholderWhenZero() {
        assertEquals("--", formatMovieRating(0.0))
        assertEquals("--", formatMovieRating(-0.0))
    }
}