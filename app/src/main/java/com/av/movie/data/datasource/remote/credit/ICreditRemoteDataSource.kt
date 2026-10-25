package com.av.movie.data.datasource.remote.credit

import com.av.movie.data.datasource.remote.IBaseRemoteDataSource
import com.av.movie.data.model.CreditsDTO
import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Credit

interface ICreditRemoteDataSource : IBaseRemoteDataSource<CreditsDTO, List<Credit>> {
    suspend fun getCreditOfActor(id: Int): ResultData<List<Credit>, String>
}