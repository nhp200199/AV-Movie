package com.av.movie.data.mapper.movie

import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.CastListDTO
import javax.inject.Inject

class CastDTO2Cast @Inject constructor() : Mapper<CastListDTO, CastListDTO> {
    override fun map(input: CastListDTO): CastListDTO {
        return input
    }
}