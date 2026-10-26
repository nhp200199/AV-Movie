package com.av.movie.data.repository.datasourceimp

import com.av.movie.data.api.MovieDetailApi
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.MovieDetailDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.repository.datasource.MovieDetailRemoteDataSource
import com.av.movie.domain.model.MovieDetail
import javax.inject.Inject

class MovieDetailRemoteDataSourceImp @Inject constructor(
    private val movieDetailApi: MovieDetailApi,
    mapper: Mapper<MovieDetailDTO, MovieDetail>
) : MovieDetailRemoteDataSource,
    BaseRemoteDataPaging<MovieDetailDTO, MovieDetail>(mapper) {

    override suspend fun getDetail(id: Int): ResultData<MovieDetail, String> {
        return getData { movieDetailApi.getDetail(id) }
    }
}