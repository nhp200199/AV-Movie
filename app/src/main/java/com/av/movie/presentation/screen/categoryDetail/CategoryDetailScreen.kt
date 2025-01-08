package com.av.movie.presentation.screen.categoryDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.av.movie.presentation.screen.home.MovieItem
import com.av.movie.test.MODEL_POPULAR_MOVIES
import com.av.movie.ui.theme.Grey10

@Composable
fun CategoryDetailScreen(
    name: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        TopAppBar(
            backgroundColor = Grey10
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Go back",
                        tint = Color.White
                    )
                }
                Text(
                    text = name,
                    modifier = Modifier.align(Alignment.Center),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            items(MODEL_POPULAR_MOVIES.size) {idx ->
                MovieItem(
                    movie = MODEL_POPULAR_MOVIES[idx]
                )
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryDetailScreenPreview() {
    CategoryDetailScreen(name = "test")
}