package com.av.movie.domain.repository.movie

import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.MovieDetail

interface MovieDetailRepository {
    suspend fun getDetail(id: Int): ResultData<MovieDetail, String>
}