package com.av.movie.data.repository.datasourceimp

import com.av.movie.data.api.CreditApi
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.CreditsDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.repository.datasource.CreditRemoteDataSource
import com.av.movie.domain.model.Credit
import javax.inject.Inject

class CreditRemoteDataSourceImp @Inject constructor(
    private val creditApi: CreditApi,
    mapper: Mapper<CreditsDTO, List<Credit>>
) : CreditRemoteDataSource,
    BaseRemoteDataSource<CreditsDTO, List<Credit>>(mapper) {

    override suspend fun getCreditOfActor(id: Int): ResultData<List<Credit>, String> {
        return getData { creditApi.getCreditOfActor(id) }
    }
}