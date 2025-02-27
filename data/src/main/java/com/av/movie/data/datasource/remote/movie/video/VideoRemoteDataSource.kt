package com.av.movie.data.datasource.remote.movie.video

import com.av.movie.data.api.retrofit.service.IVideoService
import com.av.movie.data.datasource.remote.BaseRemoteDataListSource
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.Video
import com.av.movie.data.model.VideoDTO
import com.av.movie.data.model.VideoListDTO
import javax.inject.Inject

class VideoRemoteDataSource @Inject constructor(
    private val videoService: IVideoService,
    mapper: Mapper<VideoListDTO, VideoListDTO>
) : IVideoRemoteDataSource,
    BaseRemoteDataListSource<VideoListDTO, VideoListDTO>(mapper) {
    override suspend fun getVideoForMovie(id: Int): ResultData<VideoListDTO> {
        return getData { videoService.getVideosForMovie(id) }
    }
}