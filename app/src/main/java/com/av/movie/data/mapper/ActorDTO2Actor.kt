package com.av.movie.data.mapper

import com.av.movie.data.model.ActorDTO
import com.av.movie.domain.model.Actor
import javax.inject.Inject

class ActorDTO2Actor @Inject constructor() : Mapper<ActorDTO, Actor> {
    override fun map(input: ActorDTO): Actor {
        return Actor(
            id = input.id,
            name = input.name,
            biography = input.biography,
            knownForDepartment = input.knownForDepartment
        )
    }
}