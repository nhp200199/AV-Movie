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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.av.movie.presentation.screen.home.MovieItem
import com.av.movie.dataTest.MODEL_POPULAR_MOVIES
import com.av.movie.ui.theme.Grey10

enum class Category(
    val categoryName: String
) {
    POPULAR("Popular Movies"),
    TOP_RATED("Top rated")
}

@Composable
fun CategoryDetailScreenVM(
    category: Category,
    onNavigatingUp: () -> Unit,
    onNavigateToMovieDetail: (Int) -> Unit,
    viewmodel: CategoryDetailViewModel = hiltViewModel()
) {
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewmodel.getCategoryDetail(category)
    }

    CategoryDetailScreen(
        uiState = uiState,
        category = category,
        onNavigatingUp = onNavigatingUp,
        onNavigateToMovieDetail = onNavigateToMovieDetail
    )
}


@Composable
fun CategoryDetailScreen(
    uiState: CategoryUIState,
    category: Category,
    onNavigatingUp: () -> Unit,
    onNavigateToMovieDetail: (Int) -> Unit
) {
    Column {
        TopAppBar(
            backgroundColor = Grey10
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                IconButton(
                    onClick = onNavigatingUp,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go back",
                        tint = Color.White
                    )
                }
                Text(
                    text = category.categoryName,
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
            val movies = (uiState as? CategoryUIState.Success)?.movies ?: MODEL_POPULAR_MOVIES
            items(movies.size) {idx ->
                MovieItem(
                    movie = movies[idx],
                    onNavigateToMovieDetail = onNavigateToMovieDetail
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryDetailScreenPreview() {
    CategoryDetailScreen(
        uiState = CategoryUIState.Success(MODEL_POPULAR_MOVIES),
        category = Category.POPULAR,
        onNavigateToMovieDetail = {},
        onNavigatingUp = {}
    )
}