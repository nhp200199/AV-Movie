package com.av.movie.data.repository.movie

import com.av.movie.data.datasource.remote.movie.IMovieDetailRemoteDataSource
import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.MovieDetail
import com.av.movie.domain.repository.movie.MovieDetailRepository
import javax.inject.Inject

class MovieDetailRepositoryImp @Inject constructor(
    private val movieDetailRemoteDataSource: IMovieDetailRemoteDataSource
) : MovieDetailRepository {
    override suspend fun getDetail(id: Int): ResultData<MovieDetail, String> {
        return movieDetailRemoteDataSource.getDetail(id)
    }
}
