package com.av.movie.data.repository.movie.video

import com.av.movie.data.api.VideoApi
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.VideoListDTO
import com.av.movie.data.model.map
import com.av.movie.domain.model.Video
import com.av.movie.domain.repository.movie.video.VideoRepository
import javax.inject.Inject

class VideoRepositoryImp @Inject constructor(
    private val videoApi: VideoApi,
    private val mapper: Mapper<VideoListDTO, @JvmSuppressWildcards List<Video>>
) : VideoRepository {
    override suspend fun getVideoForMovie(id: Int): ResultData<List<Video>, String> {
        return videoApi.getVideosForMovie(id).map(mapper::map)
    }
}
