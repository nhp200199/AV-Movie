package com.av.movie.data.mapper.genre

import com.av.movie.data.model.Genre
import com.av.movie.data.model.GenreDTO
import com.av.movie.data.mapper.Mapper
import javax.inject.Inject

class GenreDTO2Genre @Inject constructor(): Mapper<GenreDTO, GenreDTO> {
    override fun map(input: GenreDTO): GenreDTO {
        return input
    }
}