package com.av.movie.dataTest

import com.av.movie.data.model.Movie

data class DownloadingMovie(
    val movie: Movie,
    val downloadSize: Float,
    val sizeDownloaded: Float,
)

val DOWNLOADING_SONIC_MOVIES = DownloadingMovie(
    movie = SONIC_THE_HEDGEHOG_3,
    downloadSize = 1234f,
    sizeDownloaded = 1234f
)

val DOWNLOADING_GLADIATOR_MOVIES = DownloadingMovie(
    movie = GLADIATOR_II,
    downloadSize = 1234f,
    sizeDownloaded = 234f
)

val DOWNLOADING_MOVIES = listOf(
    DOWNLOADING_SONIC_MOVIES,
    DOWNLOADING_GLADIATOR_MOVIES
)