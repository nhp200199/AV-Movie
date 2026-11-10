package com.av.movie.data.repository.movie

import com.av.movie.data.api.MovieDetailApi
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.MovieDetailDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.map
import com.av.movie.domain.model.MovieDetail
import com.av.movie.domain.repository.movie.MovieDetailRepository
import javax.inject.Inject

class MovieDetailRepositoryImp @Inject constructor(
    private val movieDetailApi: MovieDetailApi,
    private val mapper: Mapper<MovieDetailDTO, MovieDetail>
) : MovieDetailRepository {
    override suspend fun getDetail(id: Int): ResultData<MovieDetail, String> {
        return movieDetailApi.getDetail(id).map(mapper::map)
    }
}
