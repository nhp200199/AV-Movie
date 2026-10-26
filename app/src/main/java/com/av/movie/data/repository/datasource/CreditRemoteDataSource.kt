package com.av.movie.data.repository.datasource

import com.av.movie.data.model.CreditsDTO
import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Credit

interface CreditRemoteDataSource : IBaseRemoteDataSource<CreditsDTO, List<Credit>> {
    suspend fun getCreditOfActor(id: Int): ResultData<List<Credit>, String>
}