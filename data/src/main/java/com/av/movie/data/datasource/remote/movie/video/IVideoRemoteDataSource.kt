package com.av.movie.data.datasource.remote.movie.video

import com.av.movie.data.datasource.remote.IBaseRemoteDataSource
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.Video
import com.av.movie.data.model.VideoDTO

interface IVideoRemoteDataSource : IBaseRemoteDataSource<VideoDTO, Video> {
    suspend fun getVideoForMovie(id: Int): ResultData<List<Video>>
}