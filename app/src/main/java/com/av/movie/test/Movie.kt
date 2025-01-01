package com.av.movie.test

import com.av.movie.presentation.screen.home.Movie

val MODEL_MOVIE_Gladiator_II = Movie(
    1,
    "Gladiator II",
    "/2cxhvwyEwRlysAmRH4iodkvo0z5.jpg",
    6.779,
    false
)

val MODEL_MOVIE_Culpa_Tuya = Movie(
    2,
    "Your Fault",
    "/1sQA7lfcF9yUyoLYC0e6Zo3jmxE.jpg",
    7.542,
    false
)

val MODEL_MOVIE_Red_One = Movie(
    3,
    "Red One",
    "/cdqLnri3NEGcmfnqwk2TSIYtddg.jpg",
    7.022,
    false
)

val MODEL_MOVIE_Sonic = Movie(
    4,
    "Sonic the Hedgehog 3",
    "/vxMeUZ46wMYijcPSYxPCrD1ZHgx.jpg",
    7.7,
    false
)

val MODEL_MOVIE_Venom = Movie(
    5,
    "Venom: The Last Dance",
    "/aosm8NMQ3UyoBVpSxyimorCQykC.jpg",
    6.801,
    false
)

val MODEL_POPULAR_MOVIES = listOf(
    MODEL_MOVIE_Gladiator_II,
    MODEL_MOVIE_Culpa_Tuya,
    MODEL_MOVIE_Red_One,
    MODEL_MOVIE_Sonic,
    MODEL_MOVIE_Venom
)

fun getFullPosterPath(path: String): String = "https://image.tmdb.org/t/p/w440_and_h660_face${path}"