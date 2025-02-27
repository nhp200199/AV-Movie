package com.av.movie.domain.repository.movie.video

import com.av.movie.data.datasource.remote.movie.video.VideoRemoteDataSource
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.Video
import javax.inject.Inject

class VideoRepository @Inject constructor(
    private val videoRemoteDataSource: VideoRemoteDataSource
) : IVideoRepository {
    override suspend fun getVideoForMovie(id: Int): ResultData<List<Video>> {
        return videoRemoteDataSource.getVideoForMovie(id)
    }
}