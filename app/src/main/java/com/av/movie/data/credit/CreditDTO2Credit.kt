package com.av.movie.data.credit

import com.av.movie.data.Mapper
import com.av.movie.data.model.CreditsDTO

class CreditDTO2Credit : Mapper<CreditsDTO, CreditsDTO> {
    override fun map(input: CreditsDTO): CreditsDTO {
        return input
    }
}