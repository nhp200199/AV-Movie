package com.av.movie.presentation.screen.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.av.movie.presentation.screen.home.Genre
import com.av.movie.presentation.screen.onboarding.GenreItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFilterScreen(
    availableCategories: List<Genre>,
    selectedCategories: List<Genre>,
    onGenreSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            title = { Text(text = "Genres") },
            navigationIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Close"
                )
            },
            actions = {
                Text(text = "Reset")
            },
        )

        GenreSelection(
            availableGenres = availableCategories,
            selectedGenres = selectedCategories,
            onGenreSelected = onGenreSelected
        )
    }
}

@Composable
fun GenreSelection(
    availableGenres: List<Genre>,
    selectedGenres: List<Genre>,
    onGenreSelected: (Int) -> Unit,
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
                isSelected = selectedGenres.contains(availableGenres[it]),
                onGenreSelected = onGenreSelected
            )

        }
    }
}