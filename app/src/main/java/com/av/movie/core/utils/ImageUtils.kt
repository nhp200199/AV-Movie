package com.av.movie.core.utils

fun getFullPosterPath(path: String): String =
    "https://image.tmdb.org/t/p/w440_and_h660_face$path"

fun getFullBackdropPath(path: String): String =
    "https://media.themoviedb.org/t/p/w250_and_h141_face$path"

fun getCastProfilePath(path: String?): String =
    if (path == null) "" else "https://media.themoviedb.org/t/p/w138_and_h175_face/$path"
