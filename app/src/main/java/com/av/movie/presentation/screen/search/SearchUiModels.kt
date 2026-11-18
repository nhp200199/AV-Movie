package com.av.movie.presentation.screen.search

import com.av.movie.domain.model.AvailableSearchFilters
import com.av.movie.domain.model.Movie
import com.av.movie.domain.model.SearchMovieFilter
import com.av.movie.domain.model.SearchSortOption
import java.util.Locale

sealed class SearchResultState {
    data object Idle : SearchResultState()
    data object Loading : SearchResultState()
    data class Success(val movies: List<Movie>) : SearchResultState()
    data object Error : SearchResultState()
}

sealed class ActiveFilterChip {
    abstract val label: String

    data class GenreChip(val genreId: Int, override val label: String) : ActiveFilterChip()
    data class LanguageChip(val languageCode: String, override val label: String) : ActiveFilterChip()
    data class YearChip(val year: Int, override val label: String) : ActiveFilterChip()
    data class MinRatingChip(val minRating: Double, override val label: String) : ActiveFilterChip()
    data class SortChip(val sortOption: SearchSortOption, override val label: String) : ActiveFilterChip()
}

internal fun shouldShowFilterIcon(
    rawMovieCount: Int,
    displayedMovieCount: Int,
    hasActiveFilter: Boolean
): Boolean = displayedMovieCount > 2 || (hasActiveFilter && rawMovieCount > 2)

internal fun formatRatingThresholdLabel(threshold: Double): String =
    String.format(Locale.US, "%.1f+ ★", threshold)

internal fun buildActiveFilterChips(
    filter: SearchMovieFilter,
    availableFilters: AvailableSearchFilters
): List<ActiveFilterChip> {
    if (filter.isEmpty) return emptyList()

    val chips = mutableListOf<ActiveFilterChip>()
    val genreNameById = availableFilters.genres.associate { it.id to it.name }
    filter.genreIds.forEach { genreId ->
        chips.add(
            ActiveFilterChip.GenreChip(
                genreId = genreId,
                label = genreNameById[genreId] ?: "Genre $genreId"
            )
        )
    }

    filter.originalLanguage?.let { code ->
        val display = availableFilters.languages
            .firstOrNull { it.code.equals(code, ignoreCase = true) }
            ?.displayName
            ?: code.uppercase(Locale.ENGLISH)
        chips.add(ActiveFilterChip.LanguageChip(languageCode = code, label = display))
    }

    filter.releaseYear?.let { year ->
        chips.add(ActiveFilterChip.YearChip(year = year, label = year.toString()))
    }

    filter.minVoteAverage?.let { threshold ->
        chips.add(
            ActiveFilterChip.MinRatingChip(
                minRating = threshold,
                label = formatRatingThresholdLabel(threshold)
            )
        )
    }

    filter.sortBy?.let { sortOption ->
        chips.add(
            ActiveFilterChip.SortChip(
                sortOption = sortOption,
                label = "Sort: ${sortOption.label}"
            )
        )
    }

    return chips
}

internal fun removeFilterChip(
    filter: SearchMovieFilter,
    chip: ActiveFilterChip
): SearchMovieFilter {
    return when (chip) {
        is ActiveFilterChip.GenreChip -> filter.copy(genreIds = filter.genreIds - chip.genreId)
        is ActiveFilterChip.LanguageChip -> filter.copy(originalLanguage = null)
        is ActiveFilterChip.YearChip -> filter.copy(releaseYear = null)
        is ActiveFilterChip.MinRatingChip -> filter.copy(minVoteAverage = null)
        is ActiveFilterChip.SortChip -> filter.copy(sortBy = null)
    }
}
