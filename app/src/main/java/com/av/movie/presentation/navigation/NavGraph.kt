package com.av.movie.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.av.movie.presentation.screen.login.AuthenticationScreen
import com.av.movie.presentation.screen.main.MainScreen
import com.av.movie.presentation.screen.onboarding.OnBoardingScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

const val MOVIE_ID_ARGUMENT_KEY = "movieId"

@Composable
fun NavGraph() {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(key1 = systemUiController) {
        systemUiController.isNavigationBarVisible = true
//        systemUiController.systemBarsInteractionBehavior = SYSTEM_BARS_INTERACTION_BEHAVIOR_STICKY
    }

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Authentication
    ) {

        composable<Main> {
            MainScreen()
        }

        composable<Authentication> {
            AuthenticationScreen {
                navController.navigate(Onboarding) {
                    popUpTo<Authentication> {
                         inclusive = true
                    }
                }
            }
        }

        composable<Onboarding> {
            OnBoardingScreen {
                navController.navigate(Main) {
                    popUpTo<Onboarding> {
                        inclusive = true
                    }
                }
            }
        }

//        composable(route = Screen.Home.route) {
//            HomeScreen(navController = navController)
//        }
//
//        composable(
//            route = Screen.MovieDetail.route,
//            arguments = listOf(navArgument(MOVIE_ID_ARGUMENT_KEY) {
//                type = NavType.StringType
//            })
//        ) { navBackStackEntry ->
//            navBackStackEntry.arguments?.getString(MOVIE_ID_ARGUMENT_KEY)?.let { movieId ->
//                MovieDetailScreen(movieId = movieId, navController = navController)
//            }
//        }
    }
}