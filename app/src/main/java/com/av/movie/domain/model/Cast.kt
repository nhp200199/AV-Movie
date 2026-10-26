package com.av.movie.domain.model

data class Cast(
    val id: Int,
    val name: String,
    val originalName: String = "",
    val character: String,
    val profilePath: String? = null,
    val knownForDepartment: String = "",
    val gender: Int = 0,
    val adult: Boolean = false,
    val popularity: Double = 0.0,
    val castId: Int = 0,
    val creditId: String = "",
    val order: Int = 0
)