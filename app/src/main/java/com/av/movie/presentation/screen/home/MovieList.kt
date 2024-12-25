package com.av.movie.presentation.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.av.movie.domain.model.Movie
import com.av.movie.presentation.navigation.Screen

@Composable
fun MovieList(movies: List<Movie>, navController: NavController, padding: PaddingValues) {
    Column (
        Modifier.padding(start = 16.dp, top = padding.calculateTopPadding(), end = 16.dp, bottom = 0.dp)
    ){
        movies.forEach { movie ->
            MovieItem(movie = movie, navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieItem(movie: Movie, navController: NavController) {

    Card (
        modifier = Modifier
            .padding(top = 8.dp)
            .height(100.dp)
            .fillMaxWidth(),
        onClick = {
            navController.navigate(route = Screen.MovieDetail.gotoMovie("abc"))
        }
    ) {
        Row (
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = movie.title ?: "Movie",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}