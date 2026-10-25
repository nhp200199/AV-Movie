package com.av.movie.domain.repository.movie.video

import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Video

interface VideoRepository {
    suspend fun getVideoForMovie(id: Int): ResultData<List<Video>, String>
}