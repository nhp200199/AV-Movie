package com.av.movie.domain.usecase

import com.av.movie.domain.model.Movie
import com.av.movie.domain.model.SearchMovieFilter
import com.av.movie.domain.model.SearchSortOption
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class FilterAndSortSearchMoviesUseCaseTest {

    private val useCase = FilterAndSortSearchMoviesUseCase()

    private val sampleMovies = listOf(
        movie(id = 1, title = "Avatar", genres = listOf(28, 12), lang = "en", date = "2009-12-18", rating = 7.6, popularity = 120.0, votes = 31000),
        movie(id = 2, title = "Avatar: The Way of Water", genres = listOf(28, 878), lang = "en", date = "2022-12-14", rating = 8.1, popularity = 250.0, votes = 12000),
        movie(id = 3, title = "Oldboy", genres = listOf(18, 53), lang = "ko", date = "2003-11-21", rating = 8.3, popularity = 80.0, votes = 8500),
        movie(id = 4, title = "Low Rated Draft", genres = listOf(35), lang = "fr", date = "", rating = 4.2, popularity = 10.0, votes = 15)
    )

    private val genreMap = mapOf(
        28 to "Action",
        12 to "Adventure",
        878 to "Science Fiction",
        18 to "Drama",
        53 to "Thriller",
        35 to "Comedy"
    )

    @Test
    fun extractReleaseYear_parsesValidDateAndReturnsNullForBlank() {
        assertEquals(2022, useCase.extractReleaseYear("2022-12-14"))
        assertNull(useCase.extractReleaseYear(""))
        assertNull(useCase.extractReleaseYear("abcd-01-01"))
    }

    @Test
    fun extractAvailableFilters_derivesAllFiveSectionsFromSearchResults() {
        val available = useCase.extractAvailableFilters(sampleMovies, genreMap)

        assertEquals(
            listOf("Action", "Adventure", "Comedy", "Drama", "Science Fiction", "Thriller"),
            available.genres.map { it.name }
        )
        assertEquals(listOf("en", "fr", "ko"), available.languages.map { it.code })
        assertEquals(listOf(2022, 2009, 2003), available.releaseYears)
        assertEquals(listOf(8.0, 7.0, 6.0, 5.0), available.ratingThresholds)
        assertEquals(SearchSortOption.entries, available.sortOptions)
    }

    @Test
    fun applyFilterAndSort_filtersByGenreLanguageYearAndRating() {
        val filter = SearchMovieFilter(
            genreIds = setOf(28),
            originalLanguage = "en",
            releaseYear = 2022,
            minVoteAverage = 8.0
        )

        val result = useCase.applyFilterAndSort(sampleMovies, filter)
        assertEquals(listOf(2), result.map { it.id })
    }

    @Test
    fun applyFilterAndSort_sortsByPopularityLatestRatingAndVotes() {
        assertEquals(
            listOf(2, 1, 3, 4),
            useCase.applyFilterAndSort(sampleMovies, SearchMovieFilter(sortBy = SearchSortOption.POPULARITY)).map { it.id }
        )
        assertEquals(
            listOf(2, 1, 3, 4),
            useCase.applyFilterAndSort(sampleMovies, SearchMovieFilter(sortBy = SearchSortOption.LATEST_RELEASE)).map { it.id }
        )
        assertEquals(
            listOf(3, 2, 1, 4),
            useCase.applyFilterAndSort(sampleMovies, SearchMovieFilter(sortBy = SearchSortOption.TOP_RATED)).map { it.id }
        )
        assertEquals(
            listOf(1, 2, 3, 4),
            useCase.applyFilterAndSort(sampleMovies, SearchMovieFilter(sortBy = SearchSortOption.MOST_VOTED)).map { it.id }
        )
    }

    private fun movie(
        id: Int,
        title: String,
        genres: List<Int>,
        lang: String,
        date: String,
        rating: Double,
        popularity: Double,
        votes: Int
    ) = Movie(
        adult = false,
        backdropPath = "",
        genreIds = genres,
        id = id,
        originalLanguage = lang,
        originalTitle = title,
        overview = "",
        popularity = popularity,
        posterPath = "",
        releaseDate = date,
        title = title,
        video = false,
        voteAverage = rating,
        voteCount = votes
    )
}
