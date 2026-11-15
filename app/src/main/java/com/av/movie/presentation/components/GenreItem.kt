package com.av.movie.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.av.avmovie.R
import com.av.movie.core.utils.conditional
import com.av.movie.dataTest.MODEL_GENRE_ACTION
import com.av.movie.domain.model.Genre
import com.av.movie.ui.theme.Blue90
import com.av.movie.ui.theme.DarkSurface
import kotlin.math.abs

private val genreDrawableById = mapOf(
    28 to R.drawable.genre_action,
    12 to R.drawable.genre_adventure,
    16 to R.drawable.genre_animation,
    35 to R.drawable.genre_comedy,
    80 to R.drawable.genre_crime,
    99 to R.drawable.genre_documentation,
    18 to R.drawable.genre_drama,
    10751 to R.drawable.genre_family,
    14 to R.drawable.genre_fantasy,
    36 to R.drawable.genre_history,
    27 to R.drawable.genre_horror,
    10402 to R.drawable.genre_music,
    9648 to R.drawable.genre_mystery,
    10749 to R.drawable.genre_romance,
    878 to R.drawable.genre_science_fiction,
    10770 to R.drawable.genre_tv_movie,
    53 to R.drawable.genre_thriller,
    10752 to R.drawable.genre_war,
    37 to R.drawable.genre_western
)

private val genreDrawableByName = mapOf(
    "action" to R.drawable.genre_action,
    "adventure" to R.drawable.genre_adventure,
    "animation" to R.drawable.genre_animation,
    "comedy" to R.drawable.genre_comedy,
    "crime" to R.drawable.genre_crime,
    "documentary" to R.drawable.genre_documentation,
    "documentation" to R.drawable.genre_documentation,
    "drama" to R.drawable.genre_drama,
    "family" to R.drawable.genre_family,
    "fantasy" to R.drawable.genre_fantasy,
    "history" to R.drawable.genre_history,
    "horror" to R.drawable.genre_horror,
    "music" to R.drawable.genre_music,
    "mystery" to R.drawable.genre_mystery,
    "romance" to R.drawable.genre_romance,
    "science fiction" to R.drawable.genre_science_fiction,
    "sci-fi" to R.drawable.genre_science_fiction,
    "tv movie" to R.drawable.genre_tv_movie,
    "thriller" to R.drawable.genre_thriller,
    "war" to R.drawable.genre_war,
    "western" to R.drawable.genre_western
)

// Medium-to-bright palette so fallback genre cards remain vibrant and legible without dark backgrounds.
private val fallbackGenreColors = listOf(
    Color(0xFF2E86DE),
    Color(0xFF10AC84),
    Color(0xFFEE5253),
    Color(0xFFE67E22),
    Color(0xFF8854D0),
    Color(0xFF00A8CC),
    Color(0xFFD63083),
    Color(0xFF20BF6B),
    Color(0xFFF39C12),
    Color(0xFF4B7BEC)
)

@DrawableRes
private fun Genre.backgroundDrawableRes(): Int? =
    genreDrawableById[id] ?: genreDrawableByName[name.trim().lowercase()]

private fun Genre.fallbackBackgroundColor(): Color {
    val seed = (id * 31) xor name.trim().lowercase().hashCode()
    val index = abs(seed) % fallbackGenreColors.size
    return fallbackGenreColors[index]
}

@Composable
fun GenreItem(
    genre: Genre,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onGenreSelected: ((Genre) -> Unit)? = null
) {
    val backgroundRes = remember(genre.id, genre.name) { genre.backgroundDrawableRes() }
    val fallbackColor = remember(genre.id, genre.name) { genre.fallbackBackgroundColor() }
    val cardShape = RoundedCornerShape(8.dp)

    Box(
        modifier = modifier
            .defaultMinSize(minHeight = 76.dp)
            .clip(cardShape)
            .background(DarkSurface)
            .conditional(
                condition = isSelected,
                ifTrue = { border(width = 1.5.dp, color = Blue90, shape = cardShape) }
            )
            .conditional(
                condition = onGenreSelected != null,
                ifTrue = { clickable { onGenreSelected?.invoke(genre) } }
            )
    ) {
        if (backgroundRes != null) {
            if (LocalInspectionMode.current) {
                Image(
                    painter = painterResource(backgroundRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alpha = if (isSelected) 1f else 0.9f,
                    modifier = Modifier.matchParentSize()
                )
            } else {
                val context = LocalContext.current
                val imageRequest = remember(backgroundRes, context) {
                    ImageRequest.Builder(context)
                        .data(backgroundRes)
                        .build()
                }
                AsyncImage(
                    model = imageRequest,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alpha = if (isSelected) 1f else 0.9f,
                    modifier = Modifier.matchParentSize()
                )
            }
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.35f))
            )
        } else {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(fallbackColor)
                    .background(Color.Black.copy(alpha = 0.15f))
            )
        }

        Text(
            text = genre.name,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 8.dp),
            style = TextStyle(
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        )

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

@Preview(showBackground = true)
@Composable
fun GenreItemPreview() {
    GenreItem(
        genre = MODEL_GENRE_ACTION,
        modifier = Modifier.width(150.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GenreItemFallbackPreview() {
    GenreItem(
        genre = Genre(id = 999, name = "Indie"),
        modifier = Modifier.width(150.dp)
    )
}