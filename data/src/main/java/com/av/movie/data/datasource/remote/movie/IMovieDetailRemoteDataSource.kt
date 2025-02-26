package com.av.movie.data.datasource.remote.movie

import com.av.movie.data.datasource.remote.IBaseRemoteDataPaging
import com.av.movie.data.model.MovieDetail
import com.av.movie.data.model.MovieDetailDTO
import com.av.movie.data.model.ResultData

interface IMovieDetailRemoteDataSource: IBaseRemoteDataPaging<MovieDetailDTO, MovieDetail> {
    suspend fun getDetail(id: Int): ResultData<MovieDetail>
}