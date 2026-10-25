package com.av.movie.domain.repository.credit

import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Credit

interface CreditRepository {
    suspend fun getCreditOfActor(id: Int): ResultData<List<Credit>, String>
}