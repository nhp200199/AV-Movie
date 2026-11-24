package com.av.movie.domain.usecase

import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Cast
import com.av.movie.domain.model.Genre
import com.av.movie.domain.model.Movie
import com.av.movie.domain.model.MovieDetail
import com.av.movie.domain.model.Video
import com.av.movie.domain.repository.MovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetFullDetailMovieUseCaseTest {

    @Test
    fun invoke_returnsFullDetailWhenAllEndpointsSucceed() = runBlocking {
        val repository = FakeMovieRepository(
            detailResult = ResultData.Success(sampleMovieDetail()),
            castsResult = ResultData.Success(listOf(Cast(id = 1, name = "Sam", character = "Jake"))),
            videosResult = ResultData.Success(listOf(Video(id = "v1", name = "Trailer 1"))),
            recommendationsResult = ResultData.Success(listOf(sampleMovie(id = 99)))
        )

        val result = GetFullDetailMovieUseCase(repository)(76600)
        assertTrue(result is ResultData.Success)
        val data = (result as ResultData.Success).data
        assertEquals(76600, data.movieDetail.id)
        assertEquals(1, data.casts.size)
        assertEquals(1, data.videos.size)
        assertEquals(1, data.recommendations.size)
    }

    @Test
    fun invoke_fallsBackToEmptyListsWhenSecondaryEndpointsFail() = runBlocking {
        val repository = FakeMovieRepository(
            detailResult = ResultData.Success(sampleMovieDetail()),
            castsResult = ResultData.ApiError("casts error"),
            videosResult = ResultData.OperationError(RuntimeException("videos error")),
            recommendationsResult = ResultData.ApiError("rec error")
        )

        val result = GetFullDetailMovieUseCase(repository)(76600)
        assertTrue(result is ResultData.Success)
        val data = (result as ResultData.Success).data
        assertEquals("Avatar: The Way of Water", data.movieDetail.title)
        assertTrue(data.casts.isEmpty())
        assertTrue(data.videos.isEmpty())
        assertTrue(data.recommendations.isEmpty())
    }

    @Test
    fun invoke_returnsErrorWhenPrimaryDetailFails() = runBlocking {
        val repository = FakeMovieRepository(
            detailResult = ResultData.ApiError("not found"),
            castsResult = ResultData.Success(emptyList()),
            videosResult = ResultData.Success(emptyList()),
            recommendationsResult = ResultData.Success(emptyList())
        )

        val result = GetFullDetailMovieUseCase(repository)(76600)
        assertTrue(result !is ResultData.Success)
    }

    private class FakeMovieRepository(
        private val detailResult: ResultData<MovieDetail, String>,
        private val castsResult: ResultData<List<Cast>, String>,
        private val videosResult: ResultData<List<Video>, String>,
        private val recommendationsResult: ResultData<List<Movie>, String>
    ) : MovieRepository {
        override suspend fun getNowPlayingMovies(page: Int) = ResultData.Success(emptyList<Movie>())
        override suspend fun getPopularMovies(page: Int) = ResultData.Success(emptyList<Movie>())
        override suspend fun getTopRatedMovies(page: Int) = ResultData.Success(emptyList<Movie>())
        override suspend fun getUpcomingMovies(page: Int) = ResultData.Success(emptyList<Movie>())
        override suspend fun searchMovie(query: String) = ResultData.Success(emptyList<Movie>())
        override suspend fun getRecommendationsForMovie(id: Int) = recommendationsResult
        override suspend fun getMovieDetail(id: Int) = detailResult
        override suspend fun getCastForMovie(id: Int) = castsResult
        override suspend fun getVideoForMovie(id: Int) = videosResult
        override suspend fun getMovieGenres() = ResultData.Success(emptyList<Genre>())
    }

    private fun sampleMovieDetail() = MovieDetail(
        adult = false,
        backdropPath = "/backdrop.jpg",
        genres = listOf(Genre(28, "Action")),
        id = 76600,
        originalLanguage = "en",
        originalTitle = "Avatar: The Way of Water",
        overview = "Set more than a decade after the events of the first film.",
        popularity = 1000.0,
        posterPath = "/poster.jpg",
        releaseDate = "2022-12-14",
        title = "Avatar: The Way of Water",
        video = false,
        voteAverage = 7.7,
        voteCount = 12000,
        duration = 192,
        country = "United States of America"
    )

    private fun sampleMovie(id: Int) = Movie(
        adult = false,
        backdropPath = "/b.jpg",
        genreIds = listOf(28),
        id = id,
        originalLanguage = "en",
        originalTitle = "Rec",
        overview = "Overview",
        popularity = 50.0,
        posterPath = "/p.jpg",
        releaseDate = "2023-01-01",
        title = "Rec",
        video = false,
        voteAverage = 7.0,
        voteCount = 100
    )
}
