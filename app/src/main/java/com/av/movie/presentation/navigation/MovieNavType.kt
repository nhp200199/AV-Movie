package com.av.movie.presentation.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.av.movie.domain.model.Movie
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.nio.charset.StandardCharsets
import java.util.Base64

@Serializable
data class MovieNavPayload(
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val isFavorite: Boolean = false
)

fun Movie.toNavPayload(): MovieNavPayload = MovieNavPayload(
    adult = adult,
    backdropPath = backdropPath,
    genreIds = genreIds,
    id = id,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
    isFavorite = isFavorite
)

fun MovieNavPayload.toDomain(): Movie = Movie(
    adult = adult,
    backdropPath = backdropPath,
    genreIds = genreIds,
    id = id,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
    isFavorite = isFavorite
)

private val navJson = Json { ignoreUnknownKeys = true }

internal fun encodeMovieNavPayload(payload: MovieNavPayload): String {
    val json = navJson.encodeToString(MovieNavPayload.serializer(), payload)
    return Base64.getUrlEncoder().withoutPadding()
        .encodeToString(json.toByteArray(StandardCharsets.UTF_8))
}

internal fun decodeMovieNavPayload(value: String): MovieNavPayload {
    val decodedBytes = Base64.getUrlDecoder().decode(value)
    val json = String(decodedBytes, StandardCharsets.UTF_8)
    return navJson.decodeFromString(MovieNavPayload.serializer(), json)
}

val MovieNavType = object : NavType<MovieNavPayload>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): MovieNavPayload? {
        return bundle.getString(key)?.let(::decodeMovieNavPayload)
    }

    override fun parseValue(value: String): MovieNavPayload {
        return decodeMovieNavPayload(value)
    }

    override fun serializeAsValue(value: MovieNavPayload): String {
        return encodeMovieNavPayload(value)
    }

    override fun put(bundle: Bundle, key: String, value: MovieNavPayload) {
        bundle.putString(key, encodeMovieNavPayload(value))
    }
}
