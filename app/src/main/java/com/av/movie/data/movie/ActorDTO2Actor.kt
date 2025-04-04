package com.av.movie.data.movie

import com.av.movie.data.Mapper
import com.av.movie.data.model.Actor
import com.av.movie.data.model.ActorDTO
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