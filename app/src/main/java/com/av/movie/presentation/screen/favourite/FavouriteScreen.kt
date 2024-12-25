package com.av.movie.presentation.screen.favourite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.av.avmovie.R

@Composable
fun FavouriteScreen(navController: NavController) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.fav_background),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                FavouriteTopBar(navController = navController)
            },
            content = { padding ->
                FavouriteList(paddingValues = padding)
            }
        )
    }

}