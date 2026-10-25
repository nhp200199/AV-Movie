package com.av.movie.data.repository.movie.video

import com.av.movie.data.datasource.remote.movie.video.IVideoRemoteDataSource
import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Video
import com.av.movie.domain.repository.movie.video.VideoRepository
import javax.inject.Inject

class VideoRepositoryImp @Inject constructor(
    private val videoRemoteDataSource: IVideoRemoteDataSource
) : VideoRepository {
    override suspend fun getVideoForMovie(id: Int): ResultData<List<Video>, String> {
        return videoRemoteDataSource.getVideoForMovie(id)
    }
}
