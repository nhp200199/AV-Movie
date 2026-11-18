package com.av.movie.domain.model

enum class SearchSortOption(val label: String) {
    POPULARITY("Popularity"),
    LATEST_RELEASE("Latest Release"),
    TOP_RATED("Top Rated"),
    MOST_VOTED("Most Voted")
}

data class LanguageOption(
    val code: String,
    val displayName: String
)

data class SearchMovieFilter(
    val genreIds: Set<Int> = emptySet(),
    val originalLanguage: String? = null,
    val releaseYear: Int? = null,
    val minVoteAverage: Double? = null,
    val sortBy: SearchSortOption? = null
) {
    val isEmpty: Boolean
        get() = this == EMPTY

    companion object {
        val EMPTY = SearchMovieFilter()
    }
}

data class AvailableSearchFilters(
    val genres: List<Genre> = emptyList(),
    val languages: List<LanguageOption> = emptyList(),
    val releaseYears: List<Int> = emptyList(),
    val ratingThresholds: List<Double> = emptyList(),
    val sortOptions: List<SearchSortOption> = SearchSortOption.entries
) {
    companion object {
        val EMPTY = AvailableSearchFilters()
    }
}
