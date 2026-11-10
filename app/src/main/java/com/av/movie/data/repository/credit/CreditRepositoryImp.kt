package com.av.movie.data.repository.credit

import com.av.movie.data.api.CreditApi
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.CreditsDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.map
import com.av.movie.domain.model.Credit
import com.av.movie.domain.repository.credit.CreditRepository
import javax.inject.Inject

class CreditRepositoryImp @Inject constructor(
    private val creditApi: CreditApi,
    private val mapper: Mapper<CreditsDTO, @JvmSuppressWildcards List<Credit>>
) : CreditRepository {
    override suspend fun getCreditOfActor(id: Int): ResultData<List<Credit>, String> {
        return creditApi.getCreditOfActor(id).map(mapper::map)
    }
}
