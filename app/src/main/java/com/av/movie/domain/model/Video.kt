package com.av.movie.domain.model

data class Video(
    val id: String,
    val name: String,
    val key: String = "",
    val site: String = "",
    val size: Int = 0,
    val type: String = "",
    val official: Boolean = false,
    val publishedAt: String = ""
)