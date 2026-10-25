package com.av.movie.data.model

import com.google.gson.annotations.SerializedName

data class GenreDTO(
    @SerializedName("genres")
    val genres: List<GenreItemDTO>
)

data class GenreItemDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)