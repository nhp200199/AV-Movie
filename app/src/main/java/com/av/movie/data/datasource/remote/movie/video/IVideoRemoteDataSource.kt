package com.av.movie.data.datasource.remote.movie.video

import com.av.movie.data.datasource.remote.IBaseRemoteDataSource
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.VideoListDTO
import com.av.movie.domain.model.Video

interface IVideoRemoteDataSource : IBaseRemoteDataSource<VideoListDTO, List<Video>> {
    suspend fun getVideoForMovie(id: Int): ResultData<List<Video>, String>
}