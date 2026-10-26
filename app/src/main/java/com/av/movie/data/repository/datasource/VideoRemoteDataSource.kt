package com.av.movie.data.repository.datasource

import com.av.movie.data.model.ResultData
import com.av.movie.data.model.VideoListDTO
import com.av.movie.domain.model.Video

interface VideoRemoteDataSource : IBaseRemoteDataSource<VideoListDTO, List<Video>> {
    suspend fun getVideoForMovie(id: Int): ResultData<List<Video>, String>
}