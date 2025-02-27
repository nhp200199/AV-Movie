package com.av.movie.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.av.movie.presentation.screen.login.AuthenticationScreen
import com.av.movie.presentation.screen.main.MainScreen
import com.av.movie.presentation.screen.movieDetail.MovieDetailScreen
import com.av.movie.presentation.screen.movieDetail.MovieDetailScreenVM
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
        startDestination = Main
    ) {

        composable<Main> {
            MainScreen(
                onNavigateMovieDetail = { movieId ->
                    navController.navigate(MovieDetail(movieId))
                }
            )
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

        composable<MovieDetail> {  backStackEntry ->
            val movieDetail = backStackEntry.toRoute<MovieDetail>()
            MovieDetailScreenVM(movieDetail.id)
        }
    }
}