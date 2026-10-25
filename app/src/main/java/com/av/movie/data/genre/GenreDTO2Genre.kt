package com.av.movie.data.genre

import com.av.movie.data.Mapper
import com.av.movie.data.model.GenreDTO
import com.av.movie.domain.model.Genre
import javax.inject.Inject

class GenreDTO2Genre @Inject constructor(): Mapper<GenreDTO, List<Genre>> {
    override fun map(input: GenreDTO): List<Genre> {
        return input.genres.map {
            Genre(
                id = it.id,
                name = it.name
            )
        }
    }
}