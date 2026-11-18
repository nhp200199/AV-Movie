package com.av.movie.domain.usecase

import com.av.movie.domain.model.AvailableSearchFilters
import com.av.movie.domain.model.Genre
import com.av.movie.domain.model.LanguageOption
import com.av.movie.domain.model.Movie
import com.av.movie.domain.model.SearchMovieFilter
import com.av.movie.domain.model.SearchSortOption
import java.util.Locale
import javax.inject.Inject

/**
 * Extracts dynamic filter options from search API results and applies
 * local filtering and sorting aligned with TMDB discover/movie criteria.
 */
class FilterAndSortSearchMoviesUseCase @Inject constructor() {

    fun extractReleaseYear(releaseDate: String): Int? {
        val yearToken = releaseDate.trim().take(4)
        return if (yearToken.length == 4 && yearToken.all { it.isDigit() }) {
            yearToken.toIntOrNull()
        } else {
            null
        }
    }

    fun extractAvailableFilters(
        movies: List<Movie>,
        genreNameById: Map<Int, String>
    ): AvailableSearchFilters {
        if (movies.isEmpty()) return AvailableSearchFilters.EMPTY

        val genres = movies
            .flatMap { it.genreIds }
            .distinct()
            .map { id -> Genre(id = id, name = genreNameById[id] ?: "Genre $id") }
            .sortedBy { it.name }

        val languages = movies
            .map { it.originalLanguage.trim().lowercase(Locale.ENGLISH) }
            .filter { it.isNotEmpty() }
            .distinct()
            .map { code -> LanguageOption(code = code, displayName = resolveLanguageDisplayName(code)) }
            .sortedBy { it.displayName }

        val releaseYears = movies
            .mapNotNull { extractReleaseYear(it.releaseDate) }
            .distinct()
            .sortedDescending()

        val ratingThresholds = RATING_THRESHOLDS.filter { threshold ->
            movies.any { it.voteAverage >= threshold }
        }

        return AvailableSearchFilters(
            genres = genres,
            languages = languages,
            releaseYears = releaseYears,
            ratingThresholds = ratingThresholds,
            sortOptions = SearchSortOption.entries
        )
    }

    fun applyFilterAndSort(
        movies: List<Movie>,
        filter: SearchMovieFilter
    ): List<Movie> {
        val filtered = movies.filter { movie ->
            val matchesGenre = filter.genreIds.isEmpty() || movie.genreIds.any { it in filter.genreIds }
            val matchesLanguage = filter.originalLanguage == null ||
                movie.originalLanguage.equals(filter.originalLanguage, ignoreCase = true)
            val matchesYear = filter.releaseYear == null ||
                extractReleaseYear(movie.releaseDate) == filter.releaseYear
            val matchesRating = filter.minVoteAverage == null ||
                movie.voteAverage >= filter.minVoteAverage

            matchesGenre && matchesLanguage && matchesYear && matchesRating
        }

        return when (filter.sortBy) {
            null -> filtered
            SearchSortOption.POPULARITY -> filtered.sortedByDescending { it.popularity }
            SearchSortOption.LATEST_RELEASE -> filtered.sortedByDescending { it.releaseDate.ifBlank { "0000-00-00" } }
            SearchSortOption.TOP_RATED -> filtered.sortedByDescending { it.voteAverage }
            SearchSortOption.MOST_VOTED -> filtered.sortedByDescending { it.voteCount }
        }
    }

    private fun resolveLanguageDisplayName(code: String): String {
        val display = Locale.forLanguageTag(code).getDisplayLanguage(Locale.ENGLISH)
        return if (display.isBlank() || display.equals(code, ignoreCase = true)) {
            code.uppercase(Locale.ENGLISH)
        } else {
            display
        }
    }

    companion object {
        private val RATING_THRESHOLDS = listOf(9.0, 8.0, 7.0, 6.0, 5.0)
    }
}
