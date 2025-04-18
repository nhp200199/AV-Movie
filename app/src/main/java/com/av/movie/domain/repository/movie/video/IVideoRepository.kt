package com.av.movie.domain.repository.movie.video

import com.av.movie.data.model.ResultData
import com.av.movie.data.model.VideoListDTO

interface IVideoRepository {
    suspend fun getVideoForMovie(id: Int): ResultData<VideoListDTO, String>
}