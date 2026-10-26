package com.av.movie.data.mapper

import com.av.movie.data.model.CastListDTO
import com.av.movie.domain.model.Cast
import javax.inject.Inject

class CastDTO2Cast @Inject constructor() : Mapper<CastListDTO, @JvmSuppressWildcards List<Cast>> {
    override fun map(input: CastListDTO): List<Cast> {
        return input.cast.map {
            Cast(
                id = it.id,
                name = it.name,
                originalName = it.originalName,
                character = it.character,
                profilePath = it.profilePath,
                knownForDepartment = it.knownForDepartment,
                gender = it.gender,
                adult = it.adult,
                popularity = it.popularity,
                castId = it.castId,
                creditId = it.creditId,
                order = it.order
            )
        }
    }
}