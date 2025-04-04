package com.av.movie.data.genre

import com.av.movie.data.model.GenreDTO
import com.av.movie.data.Mapper
import javax.inject.Inject

class GenreDTO2Genre @Inject constructor(): Mapper<GenreDTO, GenreDTO> {
    override fun map(input: GenreDTO): GenreDTO {
        return input
    }
}