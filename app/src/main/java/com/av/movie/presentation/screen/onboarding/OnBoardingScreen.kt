package com.av.movie.presentation.screen.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.av.avmovie.R
import com.av.movie.dataTest.ALL_GENRES
import com.av.movie.dataTest.MODEL_GENRE_ACTION
import com.av.movie.presentation.screen.home.Genre
import com.av.movie.ui.theme.Blue90
import com.av.movie.ui.theme.Grey10
import com.av.movie.ui.theme.LightGrey30
import com.av.movie.utils.conditional

@Composable
fun OnBoardingScreen(
    onNavigateToMain: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Pick what you'd like to watch")
            })
        },
        backgroundColor = Grey10
    ) {
        Column(
            modifier =
                Modifier.padding(it)
        ) {
            var selectedGenres by remember { mutableStateOf(emptyList<Genre>()) }

            GenreSelection(
                genres = ALL_GENRES,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                selectedGenres = selectedGenres,
                onGenreSelected = { genre ->
                    selectedGenres = if (selectedGenres.contains(genre)) {
                        selectedGenres.filter { selected -> selected != genre }
                    } else {
                        selectedGenres + genre
                    }
                }
            )

            FooterAction(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                enabled = selectedGenres.isNotEmpty(),
                onNavigateToMain = onNavigateToMain
            )
        }
    }
}

@Composable
fun FooterAction(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    onNavigateToMain: () -> Unit
) {
    Box(
        modifier = modifier
            .background(Grey10)
    ) {
        Button(
            onClick = onNavigateToMain,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 32.dp)
                .background(
                    color = if (enabled) Blue90 else Color.White,
                    shape = RoundedCornerShape(10.dp)
                )
                .align(Alignment.Center),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            ),
            enabled = enabled
        ) {
            Text(
                text = if (enabled) "Done" else "Select at least 1",
                style = TextStyle(
                    color = if (enabled) Color.White else Color.Black,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Composable
fun GenreSelection(
    genres: List<Genre>,
    modifier: Modifier = Modifier,
    selectedGenres: List<Genre> = emptyList(),
    onGenreSelected: (Genre) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier
    ) {
        items(
            count = genres.size,
            key = { genres[it].id }
        ) {
            GenreItem(
                genres[it],
                modifier = Modifier.fillMaxWidth(),
                isSelected = selectedGenres.contains(genres[it]),
                onGenreSelected = onGenreSelected
            )

        }
    }
}

@Composable
fun GenreItem(
    genre: Genre,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onGenreSelected: (Genre) -> Unit = {}
) {
    Box(
        modifier = modifier
            .paint(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentScale = ContentScale.FillWidth,
                alpha = if (isSelected) 1f else 0.9f
            )
            .conditional(
                condition = isSelected,
                ifTrue = { border(width = 1.dp, color = Blue90) }
            )
            .clickable {
                onGenreSelected(genre)
            }
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.9f)
        ) {
            Text(
                text = genre.name,
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(
                        color = LightGrey30.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            )
        }

        if (isSelected) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "Checked",
                tint = Blue90,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 4.dp, end = 4.dp)
            )
        }
    }
}

@Preview
@Composable
fun OnBoardingScreenPreview() {
    OnBoardingScreen()
}

@Preview(showBackground = true)
@Composable
fun GenreItemPreview() {
    GenreItem(
        genre = MODEL_GENRE_ACTION,
        modifier = Modifier
            .width(150.dp)
    )
}