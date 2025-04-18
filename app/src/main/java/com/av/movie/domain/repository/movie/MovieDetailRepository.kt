package com.av.movie.domain.repository.movie

import com.av.movie.data.datasource.remote.movie.MovieDetailRemoteDataSource
import com.av.movie.data.model.MovieDetail
import com.av.movie.data.model.ResultData
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(
    private val movieDetailRemoteDataSource: MovieDetailRemoteDataSource
): IMovieDetailRepository {
    override suspend fun getDetail(id: Int): ResultData<MovieDetail, String> {
        return movieDetailRemoteDataSource.getDetail(id)
    }
}