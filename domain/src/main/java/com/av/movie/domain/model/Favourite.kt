package com.av.movie.domain.model

data class Favourite (
    var id: Int,
    var title: String?,
    var imgRes: Int,
    var imgUrl: String = ""
)