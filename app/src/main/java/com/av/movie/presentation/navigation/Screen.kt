package com.av.movie.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen(val route: String) {

    data object Home: Screen("home_screen")

    data object MovieDetail: Screen("movie_detail_screen/{movieId}") {
        fun gotoMovie(movieId: String) = "movie_detail_screen/$movieId"
    }

    data object Main: Screen("main_screen")
}

// Top level screens
@Serializable
object Main

@Serializable
object Home
@Serializable
object Explore
@Serializable
object Profile
@Serializable
object Favourites
@Serializable
object Authentication

@Serializable
object Onboarding

@Serializable
data class CategoryDetail(val category: String)

@Serializable
object Nested