package com.av.movie.data.datasource.remote.credit

import com.av.movie.data.datasource.remote.IBaseRemoteDataSource
import com.av.movie.data.model.CreditsDTO
import com.av.movie.data.model.ResultData

interface ICreditRemoteDataSource : IBaseRemoteDataSource<CreditsDTO, CreditsDTO> {
    suspend fun getCreditOfActor(id: Int): ResultData<CreditsDTO>
}