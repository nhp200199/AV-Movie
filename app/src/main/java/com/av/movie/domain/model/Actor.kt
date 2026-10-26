package com.av.movie.domain.model

data class Actor(
    val id: Int,
    val name: String,
    val biography: String,
    val knownForDepartment: String
)