package com.av.movie.data.mapper.movie

import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.Video
import com.av.movie.data.model.VideoDTO
import com.av.movie.data.model.VideoListDTO
import javax.inject.Inject

class VideoDTO2Video @Inject constructor() : Mapper<VideoListDTO, VideoListDTO> {
    override fun map(input: VideoListDTO): VideoListDTO {
        return input
    }
}
