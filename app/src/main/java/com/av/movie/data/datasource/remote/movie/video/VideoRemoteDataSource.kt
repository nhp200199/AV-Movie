package com.av.movie.data.datasource.remote.movie.video

import com.av.movie.data.Mapper
import com.av.movie.data.datasource.remote.BaseRemoteDataSource
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.VideoListDTO
import com.av.movie.data.retrofit.service.IVideoService
import com.av.movie.domain.model.Video
import javax.inject.Inject

class VideoRemoteDataSource @Inject constructor(
    private val videoService: IVideoService,
    mapper: Mapper<VideoListDTO, List<Video>>
) : IVideoRemoteDataSource,
    BaseRemoteDataSource<VideoListDTO, List<Video>>(mapper) {
    override suspend fun getVideoForMovie(id: Int): ResultData<List<Video>, String> {
        return getData { videoService.getVideosForMovie(id) }
    }
}