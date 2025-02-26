package com.av.movie.data.datasource.remote.movie

import com.av.movie.data.api.retrofit.service.IVideoService
import com.av.movie.data.datasource.remote.BaseRemoteDataListSource
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.Video
import com.av.movie.data.model.VideoDTO
import javax.inject.Inject

class VideoRemoteDataSource @Inject constructor(
    private val videoService: IVideoService,
    mapper: Mapper<VideoDTO, Video>
) : IVideoRemoteDataSource,
    BaseRemoteDataListSource<VideoDTO, Video>(mapper) {
    override suspend fun getVideoForMovie(id: Int): ResultData<List<Video>> {
        return getDataList { videoService.getVideosForMovie(id) }
    }
}