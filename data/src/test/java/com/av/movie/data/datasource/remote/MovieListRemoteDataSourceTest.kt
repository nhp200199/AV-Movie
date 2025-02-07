package com.av.movie.data.datasource.remote

import com.av.movie.data.api.model.NetworkResponse
import com.av.movie.data.api.retrofit.service.MovieListService
import com.av.movie.data.testData.MODEL_POPULAR_MOVIES
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class MovieListRemoteDataSourceTest {
    lateinit var SUT: MovieListRemoteDataSource
    val movieService = mockk<MovieListService>()

    @Before
    fun setup() {
        SUT = MovieListRemoteDataSource(movieService)
    }

    @Test
    fun getNowPlayingMovies_success_dataReturned() {
        coEvery { movieService.getNowPlayingMovies() } returns NetworkResponse.Success(
            MODEL_POPULAR_MOVIES
        )
    }
}