package com.av.movie.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.av.movie.domain.model.Movie
import com.av.movie.presentation.navigation.Screen
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
            val movies = listOf(
                Movie(movieId = 1, title = "One Piece"),
                Movie(movieId = 2, title = "Naruto"),
                Movie(movieId = 3, title = "Jujitsu Kaisen")
            )
            MovieList(movies = movies, navController = navController, innerPadding)
        }
    )
}