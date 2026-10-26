package com.av.movie.data.repository.datasource

import com.av.movie.data.model.MovieDetailDTO
import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.MovieDetail

interface MovieDetailRemoteDataSource : IBaseRemoteDataPaging<MovieDetailDTO, MovieDetail> {
    suspend fun getDetail(id: Int): ResultData<MovieDetail, String>
}