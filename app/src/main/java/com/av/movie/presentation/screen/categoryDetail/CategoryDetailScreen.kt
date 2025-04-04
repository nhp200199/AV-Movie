package com.av.movie.presentation.screen.categoryDetail

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.av.movie.data.model.Movie
import com.av.movie.presentation.screen.home.MovieItem
import com.av.movie.ui.theme.Grey10
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map

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
    val flowState = viewmodel.flow.collectAsLazyPagingItems()

    CategoryDetailScreen(
        pagingState = flowState,
        uiState = uiState,
        category = category,
        onNavigatingUp = onNavigatingUp,
        onNavigateToMovieDetail = onNavigateToMovieDetail
    )
}


@Composable
fun CategoryDetailScreen(
    pagingState: LazyPagingItems<Movie>,
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

        PaginatedCategoryWrapper(
            pagingState = pagingState,
            onNavigateToMovieDetail = onNavigateToMovieDetail,
            modifier = Modifier.weight(99f)
                .fillMaxWidth()
        )
    }
}

@Composable
fun PaginatedCategoryWrapper(
    pagingState: LazyPagingItems<Movie>,
    onNavigateToMovieDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        PaginatedCategory(
            pagingState = pagingState,
            onNavigateToMovieDetail = onNavigateToMovieDetail
        )

        when (pagingState.loadState.refresh) {
            is LoadState.Loading -> {
                Log.d("PhucNguyen", "Paged data: Loading")
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            is LoadState.Error -> {
                Log.d("PhucNguyen", "Paged data: Error")
                InitialLoadErrorContainer(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {}
        }
    }
}

@Composable
fun InitialLoadErrorContainer(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = "Error", style = TextStyle(color = Color.White))
    }
}

@Composable
fun PaginatedCategory(
    pagingState: LazyPagingItems<Movie>,
    onNavigateToMovieDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        items(pagingState.itemCount) {idx ->
            pagingState[idx]?.let {
                MovieItem(
                    movie = it,
                    onNavigateToMovieDetail = onNavigateToMovieDetail
                )
            }
        }

        if (pagingState.loadState.append is LoadState.Loading) {
            item(span = {
                GridItemSpan(2)
            }) {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun CategoryDetailScreenPreview() {
//    CategoryDetailScreen(
//        uiState = CategoryUIState.Success(MODEL_POPULAR_MOVIES),
//        category = Category.POPULAR,
//        onNavigateToMovieDetail = {},
//        onNavigatingUp = {}
//    )
//}