package com.av.movie.data.repository.datasourceimp

import com.av.movie.data.api.VideoApi
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.VideoListDTO
import com.av.movie.data.repository.datasource.VideoRemoteDataSource
import com.av.movie.domain.model.Video
import javax.inject.Inject

class VideoRemoteDataSourceImp @Inject constructor(
    private val videoApi: VideoApi,
    mapper: Mapper<VideoListDTO, @JvmSuppressWildcards List<Video>>
) : VideoRemoteDataSource,
    BaseRemoteDataSource<VideoListDTO, List<Video>>(mapper) {

    override suspend fun getVideoForMovie(id: Int): ResultData<List<Video>, String> {
        return getData { videoApi.getVideosForMovie(id) }
    }
}