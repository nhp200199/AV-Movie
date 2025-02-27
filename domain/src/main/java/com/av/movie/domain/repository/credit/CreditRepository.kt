package com.av.movie.domain.repository.credit

import com.av.movie.data.datasource.remote.credit.CreditRemoteDataSource
import com.av.movie.data.model.CreditsDTO
import com.av.movie.data.model.ResultData
import javax.inject.Inject

class CreditRepository @Inject constructor(
    private val creditRemoteDataSource: CreditRemoteDataSource
) : ICreditRepository {
    override suspend fun getCreditOfActor(id: Int): ResultData<CreditsDTO> {
        return creditRemoteDataSource.getCreditOfActor(id)
    }
}