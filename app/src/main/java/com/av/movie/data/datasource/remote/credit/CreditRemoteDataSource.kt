package com.av.movie.data.datasource.remote.credit

import com.av.movie.data.Mapper
import com.av.movie.data.datasource.remote.BaseRemoteDataSource
import com.av.movie.data.model.CreditsDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.retrofit.service.ICreditService
import com.av.movie.domain.model.Credit
import javax.inject.Inject

class CreditRemoteDataSource @Inject constructor(
    private val creditService: ICreditService,
    mapper: Mapper<CreditsDTO, List<Credit>>
) : ICreditRemoteDataSource,
    BaseRemoteDataSource<CreditsDTO, List<Credit>>(mapper) {

    override suspend fun getCreditOfActor(id: Int): ResultData<List<Credit>, String> {
        return getData { creditService.getCreditOfActor(id) }
    }
}