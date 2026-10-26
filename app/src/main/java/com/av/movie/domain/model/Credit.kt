package com.av.movie.domain.model

data class Credit(
    val id: Int,
    val name: String,
    val originalName: String,
    val character: String,
    val posterPath: String?,
    val backdropPath: String?,
    val firstAirDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val overview: String,
    val popularity: Double,
    val creditId: String,
    val episodeCount: Int
)