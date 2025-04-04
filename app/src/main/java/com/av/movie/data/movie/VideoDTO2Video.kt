package com.av.movie.data.movie

import com.av.movie.data.Mapper
import com.av.movie.data.model.VideoListDTO
import javax.inject.Inject

class VideoDTO2Video @Inject constructor() : Mapper<VideoListDTO, VideoListDTO> {
    override fun map(input: VideoListDTO): VideoListDTO {
        return input
    }
}
