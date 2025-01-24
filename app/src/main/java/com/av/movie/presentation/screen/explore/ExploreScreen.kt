package com.av.movie.presentation.screen.explore

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.av.movie.dataTest.MODEL_POPULAR_MOVIES
import com.av.movie.presentation.screen.home.Movie
import com.av.movie.presentation.screen.home.MovieItem
import com.av.movie.ui.theme.Blue90
import com.av.movie.ui.theme.Cyan90

@Composable
fun ExploreScreen(
    onNavigateExploreFilterScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
        ) {
            ExploreToolBar(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(32.dp))

//        SearchHistory(
//            histories = MODEL_POPULAR_MOVIES,
//            modifier = Modifier
//                .padding(horizontal = 8.dp)
//        )

        SearchResult(
            movies = MODEL_POPULAR_MOVIES,
            TVs = MODEL_POPULAR_MOVIES,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )

//        if (true) {
//            SearchHistory()
//        } else {
//            SearchResults()
//        }
        }

        //TODO: Display this button only when the search is available
        ElevatedButton(
            onClick = onNavigateExploreFilterScreen,
            modifier = Modifier
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.elevatedButtonColors(
                contentColor = Color.Black
            )
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.List, contentDescription = "Filter")
            Text(text = "Filter")
        }
    }
}

@Composable
fun SearchResult(
    movies: List<Movie>,
    TVs: List<Movie>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        item {
            SectionWithItems(sectionName = "Movies", count = movies.size, key = { movies[it].id }) {
                MovieItem(
                    movie = movies[it],
                    modifier = Modifier.width(110.dp)
                )
            }
        }

        item {
            SectionWithItems(sectionName = "TV Shows", count = TVs.size, key = { TVs[it].id }) {
                MovieItem(
                    movie = TVs[it],
                    modifier = Modifier.width(250.dp),
                    isBackdrop = true,
                )
            }
        }
    }
}

@Composable
fun SectionWithItems(
    sectionName: String,
    count: Int,
    key: (Int) -> Any,
    modifier: Modifier = Modifier,
    content:  @Composable (LazyItemScope.(index: Int) -> Unit)
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(fontSize = 16.sp, color = Color.White, text = sectionName, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(
                count = count,
                key = key
            ) {
                content(this, it)
            }
        }
    }
}

@Composable
fun SearchHistory(
    histories: List<Movie>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "What search last",
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),

        ) {
            items(
                count = histories.size,
                key = { histories[it].id }
            ) {
                MovieItem(movie = histories[it])
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreToolBar(
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    onQueryChange = { query = it },
                    query = query,
                    onSearch = {},
                    expanded = false,
                    onExpandedChange = {},
                    placeholder = { Text("Search movies, TV shows, ...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            Icon(
                                Icons.Filled.Clear,
                                contentDescription = null,
                                modifier = Modifier.clickable { query = "" }
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            expanded = false,
            onExpandedChange = {}
        ) {}
    }
}

@Preview
@Composable
fun SearchHistoryPreview() {
    SearchHistory(
        histories = MODEL_POPULAR_MOVIES,
        modifier = Modifier
            .padding(PaddingValues(horizontal = 8.dp))
    )
}
@Preview
@Composable
fun ExploreToolBarPreview() {
    ExploreToolBar()
}

@Preview
@Composable
fun ExploreScreenPreview() {
    ExploreScreen(
        onNavigateExploreFilterScreen = {}
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SearchBarSample() {
    val textFieldState = rememberTextFieldState()
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    onQueryChange = { },
                    query = textFieldState.text.toString(),
                    onSearch = { expanded = false },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Hinted search text") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                repeat(4) { idx ->
                    val resultText = "Suggestion $idx"
                    ListItem(
                        headlineContent = { Text(resultText) },
                        supportingContent = { Text("Additional info") },
                        leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        modifier =
                        Modifier
                            .clickable {
                                textFieldState.setTextAndPlaceCursorAtEnd(resultText)
                                expanded = false
                            }
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, top = 72.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.semantics { traversalIndex = 1f },
        ) {
            val list = List(100) { "Text $it" }
            items(count = list.size) {
                Text(
                    text = list[it],
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                )
            }
        }
    }
}