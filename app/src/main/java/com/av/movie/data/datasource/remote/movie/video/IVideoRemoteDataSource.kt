package com.av.movie.data.datasource.remote.movie.video

import com.av.movie.data.datasource.remote.IBaseRemoteDataSource
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.VideoListDTO

interface IVideoRemoteDataSource : IBaseRemoteDataSource<VideoListDTO, VideoListDTO> {
    suspend fun getVideoForMovie(id: Int): ResultData<VideoListDTO, String>
}