package com.av.movie.domain.repository.movie

import com.av.movie.data.model.MovieDetail
import com.av.movie.data.model.ResultData

interface IMovieDetailRepository {
    suspend fun getDetail(id: Int): ResultData<MovieDetail, String>
}