package com.av.movie.data.datasource.remote.movie

import com.av.movie.data.datasource.remote.IBaseRemoteDataPaging
import com.av.movie.data.model.MovieDetailDTO
import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.MovieDetail

interface IMovieDetailRemoteDataSource: IBaseRemoteDataPaging<MovieDetailDTO, MovieDetail> {
    suspend fun getDetail(id: Int): ResultData<MovieDetail, String>
}