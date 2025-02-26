package com.av.movie.data.mapper.movie

import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.Video
import com.av.movie.data.model.VideoDTO
import javax.inject.Inject

class VideoDTO2Video @Inject constructor() : Mapper<VideoDTO, Video> {
    override fun map(input: VideoDTO): Video {
        return Video(
            id = input.id,
            name = input.name
        )
    }
}
