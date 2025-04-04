package com.av.movie.data.model

import com.google.gson.annotations.SerializedName

data class PagingDTO<T>(
    val page: Int,
    val results: List<T>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
)