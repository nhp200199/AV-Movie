package com.av.movie.presentation.screen.archive

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.av.avmovie.R
import com.av.movie.data.model.Movie
import com.av.movie.dataTest.MODEL_POPULAR_MOVIES
import com.av.movie.presentation.components.GradientIcon
import com.av.movie.presentation.screen.home.MovieItem
import com.av.movie.ui.theme.LightGrey10
import com.av.movie.ui.theme.LightGrey30

@Composable
fun ArchiveScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        MainContent(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier
) {
    //TODO: make the whole layout scrollable, not just History list
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        ArchiveCategory(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 12.dp)
        )
        WatchHistory(
            modifier = Modifier
                .weight(99f)
                .fillMaxWidth()
                .padding(top = 12.dp, start = 8.dp, end = 8.dp)
        )
    }
}

@Composable
fun ArchiveCategory(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        ArchiveItem(
            icon = ImageVector.vectorResource(R.drawable.ic_download),
            title = "Download",
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        ArchiveItem(
            icon = ImageVector.vectorResource(R.drawable.ic_archive),
            title = "Favorite Movies",
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ArchiveItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = LightGrey10, shape = RoundedCornerShape(12.dp))
            .background(
                color = LightGrey30,
            )
            .clickable { onClick() }
            .padding(vertical = 16.dp, horizontal = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GradientIcon(
                icon = icon,
                modifier = Modifier
                    .size(24.dp),
                contentDescription = ""
            )
            Text(
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                ),
                text = title,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .weight(99f)
            )
            GradientIcon(
                icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}

@Composable
fun WatchHistory(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "History",
            style = TextStyle(
                color = Color.White
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "The last 10 movies you watched will be here",
            style = TextStyle(
                color = Color.White
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        History(
//            modifier = Modifier
//                .height(1000.dp),
            movies = MODEL_POPULAR_MOVIES,
            onNavigateToMovieDetail = {}
        )
    }
}

@Composable
fun History(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    onNavigateToMovieDetail: (Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        items(movies.size) {idx ->
            MovieItem(
                movie = movies[idx],
                onNavigateToMovieDetail = onNavigateToMovieDetail
            )
        }
    }
}

@Preview
@Composable
fun ArchiveScreenPreview() {
    ArchiveScreen()
}

@Preview
@Composable
fun ArchiveItemPreview() {
    ArchiveItem(
        icon = ImageVector.vectorResource(R.drawable.ic_launcher_background),
        title = "Download",
        onClick = {}
    )
}