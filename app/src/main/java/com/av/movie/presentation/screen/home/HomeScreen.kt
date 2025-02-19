package com.av.movie.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.av.movie.oldClass.OldMovie
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colorScheme.primary
//    val allMovies = viewModel.latestMovie

    SideEffect {
        systemUiController.setSystemBarsColor(color = systemBarColor)
    }

    Scaffold(
        Modifier.background(MaterialTheme.colorScheme.primary),
        contentColor = MaterialTheme.colorScheme.secondary,
        topBar = {
            HomeTopBar()
        },
        content = { innerPadding ->
            val p = innerPadding // Avoid error Material3
            val movies = listOf(
                OldMovie(movieId = 1, title = "One Piece"),
                OldMovie(movieId = 2, title = "Naruto"),
                OldMovie(movieId = 3, title = "Jujitsu Kaisen")
            )
            MovieList(movies = movies, navController = navController)
        }
    )
}