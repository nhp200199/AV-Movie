package com.av.movie.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.av.movie.presentation.screen.detail.MovieDetailScreen
import com.av.movie.presentation.screen.favourite.FavouriteScreen
import com.av.movie.presentation.screen.home.HomeScreen

const val MOVIE_ID_ARGUMENT_KEY = "movieId"

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Favourite.route
    ) {
        
        composable(route = Screen.Favourite.route) {
            FavouriteScreen(navController = navController)
        }
        
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Screen.MovieDetail.route,
            arguments = listOf(navArgument(MOVIE_ID_ARGUMENT_KEY) {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString(MOVIE_ID_ARGUMENT_KEY)?.let { movieId ->
                MovieDetailScreen(movieId = movieId, navController = navController)
            }
        }
    }
}