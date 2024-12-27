package com.av.movie.presentation.navigation

sealed class Screen(val route: String) {

    data object Home: Screen("home_screen")

    data object MovieDetail: Screen("movie_detail_screen/{movieId}") {
        fun gotoMovie(movieId: String) = "movie_detail_screen/$movieId"
    }

    data object Main: Screen("main_screen")
}