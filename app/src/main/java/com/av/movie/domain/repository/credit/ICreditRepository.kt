package com.av.movie.domain.repository.credit

import com.av.movie.data.model.CreditsDTO
import com.av.movie.data.model.ResultData

interface ICreditRepository {
    suspend fun getCreditOfActor(id: Int): ResultData<CreditsDTO, String>
}