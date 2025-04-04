package com.av.movie.data.model
import com.google.gson.annotations.SerializedName

data class VideoListDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<VideoDTO>
) {
    companion object {
        val DEFAULT = VideoListDTO(0, emptyList())
    }
}

data class VideoDTO(
    @SerializedName("iso_639_1")
    val iso6391: String,
    @SerializedName("iso_3166_1")
    val iso31661: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("site")
    val site: String,
    @SerializedName("size")
    val size: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("official")
    val official: Boolean,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("id")
    val id: String
)