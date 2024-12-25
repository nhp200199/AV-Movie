package com.av.movie.presentation.screen.favourite

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.av.avmovie.R
import com.av.movie.domain.model.Favourite
import com.av.movie.ui.theme.TextFavouriteBackground

@Composable
fun FavouriteList(paddingValues: PaddingValues) {
    val favourites = listOf(
        Favourite(id = 1, title = "Movies", imgRes = R.drawable.avatar),
        Favourite(id = 2, title = "Series", imgRes = R.drawable.avengers),
        Favourite(id = 3, title = "Sport", imgRes = R.drawable.avatar),
        Favourite(id = 4, title = "Cartoons", imgRes = R.drawable.avengers),
        Favourite(id = 5, title = "Sci-Fi", imgRes = R.drawable.avatar),
        Favourite(id = 6, title = "TV Shows", imgRes = R.drawable.avengers),
        Favourite(id = 7, title = "Sci-Fi", imgRes = R.drawable.avatar),
        Favourite(id = 8, title = "TV Shows", imgRes = R.drawable.avengers),
        Favourite(id = 9, title = "Sci-Fi", imgRes = R.drawable.avatar),
        Favourite(id = 10, title = "TV Shows", imgRes = R.drawable.avengers)
    )

    LazyColumn(
        contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding(),
                start = 16.dp,
                end = 16.dp,
                bottom = 80.dp
            ),
        state = rememberLazyListState()
    ) {
        items(
            items = favourites,
            key = { fav ->
                fav.id
            }
        ) {fav ->
            FavouriteItem(favourite = fav)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteItem(favourite: Favourite) {
    Card (
        modifier = Modifier
            .padding(top = 8.dp)
            .height(100.dp)
            .fillMaxWidth(),
        border = BorderStroke(2.dp, color = Color.Blue),
        onClick = {

        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = favourite.imgRes),
                contentDescription = "Image banner",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = favourite.title ?: "",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier
                    .padding(0.dp)
                    .align(Alignment.Center)
                    .background(MaterialTheme.colorScheme.TextFavouriteBackground, shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }
    }
}