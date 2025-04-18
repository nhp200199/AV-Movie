package com.av.movie.domain.repository.movie.video

import com.av.movie.data.datasource.remote.movie.video.VideoRemoteDataSource
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.VideoListDTO
import javax.inject.Inject

class VideoRepository @Inject constructor(
    private val videoRemoteDataSource: VideoRemoteDataSource
) : IVideoRepository {
    override suspend fun getVideoForMovie(id: Int): ResultData<VideoListDTO, String> {
        return videoRemoteDataSource.getVideoForMovie(id)
    }
}