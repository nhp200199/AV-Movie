package com.av.movie.data.datasource.remote

import com.av.movie.data.api.model.NetworkResponse
import com.av.movie.data.api.model.ResultData
import com.av.movie.data.api.retrofit.service.MovieListService
import com.av.movie.data.common.exception.NoNetworkConnectionException
import com.av.movie.data.common.exception.UnknownException
import com.av.movie.data.mapper.movie.MoviePreviewDTO2Movie
import com.av.movie.data.testData.DTO_POPULAR_MOVIES
import com.av.movie.data.testData.MODEL_POPULAR_MOVIES
import com.av.movie.domain.model.Movie
import io.mockk.called
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test


class MovieListRemoteDataSourceTest {
    lateinit var SUT: MovieListRemoteDataSource
    val movieService = mockk<MovieListService>()
    val moviePreviewDTO2Movie = mockk<MoviePreviewDTO2Movie>()

    @Before
    fun setup() {
        SUT = MovieListRemoteDataSource(
            movieService,
            moviePreviewDTO2Movie
        )
    }

    @Test
    fun getNowPlayingMovies_success_dataReturned() = runTest {
        coEvery { movieService.getNowPlayingMovies() } returns NetworkResponse.Success(
            DTO_POPULAR_MOVIES
        )
        every { moviePreviewDTO2Movie.map(any()) } returnsMany MODEL_POPULAR_MOVIES

        val nowPlayingMovies = SUT.getNowPlayingMovies()

        assertThat(nowPlayingMovies, `is`(instanceOf(ResultData.Success::class.java)))
        assertThat(
            (nowPlayingMovies as ResultData.Success<List<Movie>>).data,
            `is`(MODEL_POPULAR_MOVIES))
    }

    @Test
    fun getNowPlayingMovies_noNetworkConnection_NoNetworkErrorReturned() = runTest {
        coEvery { movieService.getNowPlayingMovies() } returns NetworkResponse.NetworkError

        val nowPlayingMovies = SUT.getNowPlayingMovies()

        verify { moviePreviewDTO2Movie wasNot called }
        assertThat((nowPlayingMovies as ResultData.Error).exception, `is`(instanceOf(NoNetworkConnectionException::class.java)))
    }

    @Test
    fun getNowPlayingMovies_requestingError_apiErrorReturned() = runTest {
        coEvery { movieService.getNowPlayingMovies() } returns NetworkResponse.ApiError("", 0)

        val nowPlayingMovies = SUT.getNowPlayingMovies()

        verify { moviePreviewDTO2Movie wasNot called }
        assertThat(nowPlayingMovies, `is`(instanceOf(ResultData.Error::class.java)))
    }

    @Test
    fun getNowPlayingMovies_serverError_apiErrorReturned() = runTest {
        coEvery { movieService.getNowPlayingMovies() } returns NetworkResponse.UnknownError

        val nowPlayingMovies = SUT.getNowPlayingMovies()

        verify { moviePreviewDTO2Movie wasNot called }
        assertThat(nowPlayingMovies, `is`(instanceOf(ResultData.Error::class.java)))
        assertThat((nowPlayingMovies as ResultData.Error).exception, `is`(instanceOf(UnknownException::class.java)))
    }
}