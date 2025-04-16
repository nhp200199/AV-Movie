@file:OptIn(ExperimentalMaterial3Api::class)

package com.av.movie.presentation.screen.downloadMovie

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.av.avmovie.R
import com.av.movie.dataTest.DOWNLOADING_MOVIES
import com.av.movie.dataTest.DOWNLOADING_SONIC_MOVIES
import com.av.movie.dataTest.DownloadingMovie
import com.av.movie.dataTest.getFullBackdropPath
import com.av.movie.dataTest.getFullPosterPath
import com.av.movie.presentation.components.CommonCenterAppBar
import com.av.movie.presentation.components.GradientButton
import com.av.movie.presentation.components.GradientIcon
import com.av.movie.presentation.components.GradientText
import com.av.movie.ui.theme.Blue90
import com.av.movie.ui.theme.Grey10
import com.av.movie.ui.theme.LightGrey10
import com.av.movie.ui.theme.LightGrey30
import kotlinx.coroutines.launch
import kotlin.math.round

@Composable
fun DownloadMovieScreen(
    onNavigateUp: () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val downloadMovies = DOWNLOADING_MOVIES
    var pendingDeleteMovie by remember { mutableStateOf<Int?>(null) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CommonCenterAppBar(
                title = "Download",
                onNavigateUp = onNavigateUp
            )

            DownloadMovieSection(
                downloadingMovies = downloadMovies,
                onDeleteMovie = {
                    pendingDeleteMovie = it
                },
                modifier = Modifier
                    .weight(99f)
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            if (pendingDeleteMovie != null) {
                DeleteMovieSheet(
                    sheetState = sheetState,
                    movie = downloadMovies.first { it.movie.id == pendingDeleteMovie },
                    onDeleteMovie = {},
                    onCancelled = {
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            pendingDeleteMovie = null
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun DownloadMovieSection(
    downloadingMovies: List<DownloadingMovie>,
    modifier: Modifier = Modifier,
    onDeleteMovie: (id: Int) -> Unit = {},
    onCancelDownload: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(downloadingMovies.size) {
            DownloadItem(
                movieId = downloadingMovies[it].movie.id,
                posterPath = downloadingMovies[it].movie.posterPath,
                title = downloadingMovies[it].movie.title,
                downloadSize = downloadingMovies[it].downloadSize,
                sizeDownloaded = downloadingMovies[it].sizeDownloaded,
                onDeleteMovie = onDeleteMovie,
                onCancelDownload = onCancelDownload,
            )
        }
    }
}

@Composable
fun DownloadItem(
    movieId: Int,
    posterPath: String,
    title: String,
    downloadSize: Float,
    sizeDownloaded: Float,
    onCancelDownload: () -> Unit,
    onDeleteMovie: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val completed by remember {
        derivedStateOf {
            sizeDownloaded / downloadSize == 1f
        }
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = LightGrey10, shape = RoundedCornerShape(12.dp))
            .background(
                color = Grey10,
            )
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box {
                AsyncImage(
                    model = getFullPosterPath(posterPath),
                    contentDescription = title,
                    contentScale = ContentScale.FillHeight,
                    error = painterResource(id = R.drawable.ic_launcher_background),
                    placeholder = painterResource(id = R.drawable.ic_launcher_background),
                    modifier = Modifier
                        .alpha(if (completed) 1f else 0.5f)
                )

                if (completed) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }

            DownloadInfo(
                title = title,
                downloadSize = downloadSize,
                sizeDownloaded = sizeDownloaded,
                onCancelDownload = onCancelDownload,
                completed = completed,
                modifier = Modifier
                    .weight(99f)
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            )

            if (completed) {
                IconButton(
                    onClick = {
                        onDeleteMovie(movieId)
                    },
                    modifier = Modifier
                        .padding(4.dp)
                ) {
                    GradientIcon(
                        icon = Icons.Outlined.Clear,
                        contentDescription = "Delete",
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun DownloadSizeChip(
    sizeInMB: Float,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .background(
            color = LightGrey30,
            shape = RoundedCornerShape(6.dp)
        )
        .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        GradientText(
            text = "${formatFloat(sizeInMB)} MB",
            textStyle = TextStyle(
                fontSize = 12.sp
            )
        )
    }
}

@Composable
fun DownloadInfo(
    title: String,
    downloadSize: Float,
    sizeDownloaded: Float,
    completed: Boolean,
    onCancelDownload: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = TextStyle(
                color = Color.White
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (completed) {
            DownloadSizeChip(sizeInMB = downloadSize)
        } else {
            DownloadProgress(
                downloadSize = downloadSize,
                sizeDownloaded = sizeDownloaded,
                onCancelDownload = onCancelDownload
            )
        }

    }
}

private fun formatFloat(value: Float): String {
    val roundedValue = round(value * 10) / 10
    return if (roundedValue % 1.0 == 0.0) {
        roundedValue.toInt().toString()
    } else {
        String.format("%.1f", roundedValue)
    }
}

@Composable
fun DownloadProgress(
    downloadSize: Float,
    sizeDownloaded: Float,
    onCancelDownload: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progressInPercentage = remember(downloadSize, sizeDownloaded) {
        (sizeDownloaded / downloadSize) * 100
    }

    Column(
        modifier = modifier
    ) {
        Row {
            Text(
                text = "${formatFloat(sizeDownloaded)}/${formatFloat(downloadSize)}",
                modifier = Modifier.weight(99f),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp
                )
            )
            GradientText(
                text = "${formatFloat(progressInPercentage)}%",
                textStyle = TextStyle(
                    fontSize = 12.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            LinearProgressIndicator(
                progress = sizeDownloaded / downloadSize,
                modifier = Modifier
                    .weight(99f)
                    .height(12.dp),
                strokeCap = StrokeCap.Round,
                //TODO: make custom view to be able to use gradient color
                color = Blue90
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = "Cancel Download",
                tint = Color.White,
                modifier = Modifier
                    .size(18.dp)
                    .clickable { onCancelDownload() }
            )
        }
    }
}

@Preview
@Composable
fun DownloadMovieScreenPreview() {
    DownloadMovieScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteMovieSheet(
    movie: DownloadingMovie,
    sheetState: SheetState,
    onDeleteMovie: () -> Unit,
    onCancelled: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onCancelled,
        sheetState = sheetState,
        containerColor = Grey10,
        tonalElevation = 12.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Delete",
                style = TextStyle(
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )

            Text(
                text = "Are you sure to delete this download?",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            PendingDeleteMovie(
                movieId = movie.movie.id,
                posterPath = getFullBackdropPath(movie.movie.backdropPath),
                title = movie.movie.title,
                downloadSize = movie.downloadSize,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(color = LightGrey10)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Button(
                    onClick = onCancelled,
                    modifier = Modifier
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightGrey10,
                        contentColor = Color.Red
                    )
                ) {
                    Text(
                        text = "Cancel",
                        style = TextStyle(
                            color = Color.White
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                GradientButton(
                    onClick = onDeleteMovie,
                    modifier = Modifier
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Red
                    )
                ) {
                    Text(
                        text = "Delete",
                        style = TextStyle(
                            color = Color.White
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PendingDeleteMovie(
    modifier: Modifier = Modifier,
    movieId: Int,
    posterPath: String,
    title: String,
    downloadSize: Float,
) {
    Row(modifier = modifier) {
        AsyncImage(
            model = getFullPosterPath(posterPath),
            contentDescription = title,
            contentScale = ContentScale.FillHeight,
            error = painterResource(id = R.drawable.ic_launcher_background),
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .weight(99f)
        ) {
            Text(
                text = title,
                style = TextStyle(
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            DownloadSizeChip(sizeInMB = downloadSize)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun DeleteMovieSheetPreview() {
    DeleteMovieSheet(
        sheetState = rememberModalBottomSheetState(),
        modifier = Modifier
            .fillMaxWidth(),
        onDeleteMovie = {},
        onCancelled = {},
        movie = DOWNLOADING_SONIC_MOVIES
    )
}

@Preview
@Composable
fun DownloadItemPreview() {
    DownloadItem(
        movieId = 1,
        posterPath = "",
        title = "Avengers",
        downloadSize = 100f,
        sizeDownloaded = 100f,
        onCancelDownload = {},
        onDeleteMovie = {},
        modifier = Modifier.fillMaxWidth()
    )
}