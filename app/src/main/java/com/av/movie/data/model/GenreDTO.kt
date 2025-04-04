package com.av.movie.data.model

import com.google.gson.annotations.SerializedName

data class GenreDTO(
    @SerializedName("genres")
    val genres: List<Genre>
)