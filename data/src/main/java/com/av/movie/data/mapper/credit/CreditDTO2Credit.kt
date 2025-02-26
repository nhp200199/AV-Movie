package com.av.movie.data.mapper.credit

import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.CreditsDTO

class CreditDTO2Credit : Mapper<CreditsDTO, CreditsDTO> {
    override fun map(input: CreditsDTO): CreditsDTO {
        return input
    }
}