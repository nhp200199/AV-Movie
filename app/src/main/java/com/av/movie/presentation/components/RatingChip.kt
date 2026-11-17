package com.av.movie.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.av.avmovie.R
import com.av.movie.ui.theme.BrandGradient
import com.av.movie.ui.theme.White
import java.math.BigDecimal
import java.math.RoundingMode

internal fun formatMovieRating(rating: Double): String {
    if (rating <= 0.0) return "--"
    return BigDecimal.valueOf(rating)
        .setScale(1, RoundingMode.HALF_UP)
        .toPlainString()
}

@Composable
fun RatingChip(
    rating: Double,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                color = White.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp),
            )
            .padding(4.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.pngwing),
            contentDescription = null,
            modifier = Modifier.width(36.dp),
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = formatMovieRating(rating)
        )
    }
}

@Composable
fun RatingBadge(
    rating: Double,
    modifier: Modifier = Modifier
) {
    Text(
        text = formatMovieRating(rating),
        color = White,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier
            .background(
                brush = BrandGradient,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun RatingBadgePreview() {
    RatingBadge(rating = 9.8)
}