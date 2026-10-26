package com.av.movie.data.mapper

import com.av.movie.data.model.CreditsDTO
import com.av.movie.domain.model.Credit
import javax.inject.Inject

class CreditDTO2Credit @Inject constructor() : Mapper<CreditsDTO, List<Credit>> {
    override fun map(input: CreditsDTO): List<Credit> {
        return input.cast.map {
            Credit(
                id = it.id,
                name = it.name,
                originalName = it.originalName,
                character = it.character,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                firstAirDate = it.firstAirDate,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
                overview = it.overview,
                popularity = it.popularity,
                creditId = it.creditId,
                episodeCount = it.episodeCount
            )
        }
    }
}