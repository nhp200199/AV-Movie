package com.av.movie.data.repository.credit

import com.av.movie.data.datasource.remote.credit.ICreditRemoteDataSource
import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Credit
import com.av.movie.domain.repository.credit.CreditRepository
import javax.inject.Inject

class CreditRepositoryImp @Inject constructor(
    private val creditRemoteDataSource: ICreditRemoteDataSource
) : CreditRepository {
    override suspend fun getCreditOfActor(id: Int): ResultData<List<Credit>, String> {
        return creditRemoteDataSource.getCreditOfActor(id)
    }
}
