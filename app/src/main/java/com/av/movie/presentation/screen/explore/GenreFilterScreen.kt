package com.av.movie.presentation.screen.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.av.movie.data.model.Genre
import com.av.movie.presentation.screen.onboarding.GenreItem
import com.av.movie.ui.theme.Grey10
import com.av.movie.ui.theme.LightGrey50

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreFilterScreen(
    availableGenres: List<Genre>,
    selectedGenres: List<Genre>?,
    onGenreSelected: (Genre) -> Unit,
    onReset: () -> Unit,
    onNavigateUp: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Grey10)
    ) {
        CenterAlignedTopAppBar(
            title = { Text(text = "Genres") },
            navigationIcon = {
                IconButton(
                    onClick = onNavigateUp
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Close"
                    )
                }
            },
            actions = {
                TextButton(
                    onClick = onReset,
                    enabled = !selectedGenres.isNullOrEmpty(),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.White,
                        disabledContentColor = LightGrey50
                    )
                ) {
                    Text(text = "Reset")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Grey10,
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White
            )
        )

        GenreSelection(
            availableGenres = availableGenres,
            selectedGenres = selectedGenres,
            onGenreSelected = onGenreSelected
        )
    }
}

@Composable
fun GenreSelection(
    availableGenres: List<Genre>,
    selectedGenres: List<Genre>?,
    onGenreSelected: (Genre) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier
    ) {
        items(
            count = availableGenres.size,
            key = { availableGenres[it].id }
        ) {
            GenreItem(
                availableGenres[it],
                modifier = Modifier.fillMaxWidth(),
                isSelected = selectedGenres?.contains(availableGenres[it]) ?: false,
                onGenreSelected = onGenreSelected
            )

        }
    }
}