package com.av.movie.data.datasource.remote.movie

import com.av.movie.data.retrofit.service.IMovieDetailService
import com.av.movie.data.datasource.remote.BaseRemoteDataPaging
import com.av.movie.data.Mapper
import com.av.movie.data.model.MovieDetail
import com.av.movie.data.model.MovieDetailDTO
import com.av.movie.data.model.ResultData
import javax.inject.Inject

class MovieDetailRemoteDataSource @Inject constructor(
    private val service: IMovieDetailService,
    mapper: Mapper<MovieDetailDTO, MovieDetail>
) : IMovieDetailRemoteDataSource, BaseRemoteDataPaging<MovieDetailDTO, MovieDetail>(mapper) {

    override suspend fun getDetail(id: Int): ResultData<MovieDetail> {
        return getData { service.getDetail(id) }
    }
}