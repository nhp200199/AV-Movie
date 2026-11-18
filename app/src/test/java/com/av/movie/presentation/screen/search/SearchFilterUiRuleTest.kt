package com.av.movie.presentation.screen.search

import com.av.movie.domain.model.AvailableSearchFilters
import com.av.movie.domain.model.Genre
import com.av.movie.domain.model.LanguageOption
import com.av.movie.domain.model.SearchMovieFilter
import com.av.movie.domain.model.SearchSortOption
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SearchFilterUiRuleTest {

    @Test
    fun shouldShowFilterIcon_returnsTrueWhenRawResultsExceedTwoEvenAfterFilteringToOneItem() {
        assertFalse(shouldShowFilterIcon(rawMovieCount = 0, displayedMovieCount = 0, hasActiveFilter = false))
        assertFalse(shouldShowFilterIcon(rawMovieCount = 1, displayedMovieCount = 1, hasActiveFilter = false))
        assertFalse(shouldShowFilterIcon(rawMovieCount = 2, displayedMovieCount = 2, hasActiveFilter = false))
        assertTrue(shouldShowFilterIcon(rawMovieCount = 3, displayedMovieCount = 3, hasActiveFilter = false))
        assertTrue(shouldShowFilterIcon(rawMovieCount = 10, displayedMovieCount = 1, hasActiveFilter = true))
        assertTrue(shouldShowFilterIcon(rawMovieCount = 10, displayedMovieCount = 2, hasActiveFilter = true))
        assertTrue(shouldShowFilterIcon(rawMovieCount = 10, displayedMovieCount = 0, hasActiveFilter = true))
    }

    @Test
    fun buildActiveFilterChips_andRemoveFilterChip_handlesAllFiveFilterTypes() {
        val available = AvailableSearchFilters(
            genres = listOf(Genre(28, "Action"), Genre(12, "Adventure")),
            languages = listOf(LanguageOption("ko", "Korean")),
            releaseYears = listOf(2022),
            ratingThresholds = listOf(8.0)
        )
        var filter = SearchMovieFilter(
            genreIds = setOf(28, 12),
            originalLanguage = "ko",
            releaseYear = 2022,
            minVoteAverage = 8.0,
            sortBy = SearchSortOption.TOP_RATED
        )

        val chips = buildActiveFilterChips(filter, available)
        assertEquals(6, chips.size)
        assertEquals(
            listOf("Action", "Adventure", "Korean", "2022", "8.0+ ★", "Sort: Top Rated"),
            chips.map { it.label }
        )

        filter = removeFilterChip(filter, chips[0]) // remove Action
        assertEquals(setOf(12), filter.genreIds)

        filter = removeFilterChip(filter, chips[2]) // remove Korean
        assertEquals(null, filter.originalLanguage)

        filter = removeFilterChip(filter, chips[3]) // remove 2022
        assertEquals(null, filter.releaseYear)

        filter = removeFilterChip(filter, chips[4]) // remove 8.0+
        assertEquals(null, filter.minVoteAverage)

        filter = removeFilterChip(filter, chips[5]) // remove Sort
        assertEquals(null, filter.sortBy)
    }
}
