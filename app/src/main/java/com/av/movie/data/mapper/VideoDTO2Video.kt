package com.av.movie.data.mapper

import com.av.movie.data.model.VideoListDTO
import com.av.movie.domain.model.Video
import javax.inject.Inject

class VideoDTO2Video @Inject constructor() : Mapper<VideoListDTO, @JvmSuppressWildcards List<Video>> {
    override fun map(input: VideoListDTO): List<Video> {
        return input.results.map {
            Video(
                id = it.id,
                name = it.name,
                key = it.key,
                site = it.site,
                size = it.size,
                type = it.type,
                official = it.official,
                publishedAt = it.publishedAt
            )
        }
    }
}