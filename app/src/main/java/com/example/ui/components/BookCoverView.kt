package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BookEntity

enum class CoverSize(
    val width: Dp,
    val height: Dp,
    val titleSize: Float,
    val authorSize: Float,
    val emojiSize: Float
) {
    COMPACT(72.dp, 104.dp, 10f, 8f, 18f),
    MEDIUM(128.dp, 184.dp, 13f, 10f, 32f),
    LARGE(170.dp, 244.dp, 16f, 12f, 44f)
}

@Composable
fun BookCoverView(
    book: BookEntity,
    size: CoverSize = CoverSize.MEDIUM,
    modifier: Modifier = Modifier
) {
    val baseColor = remember(book.coverColorHex) {
        try {
            Color(android.graphics.Color.parseColor(book.coverColorHex))
        } catch (_: Exception) {
            Color(0xFF3730A3)
        }
    }

    val darkerShade = remember(baseColor) {
        baseColor.copy(
            red = (baseColor.red * 0.7f).coerceIn(0f, 1f),
            green = (baseColor.green * 0.7f).coerceIn(0f, 1f),
            blue = (baseColor.blue * 0.7f).coerceIn(0f, 1f)
        )
    }

    val spineShadow = remember {
        Brush.horizontalGradient(
            colors = listOf(
                Color.Black.copy(alpha = 0.55f),
                Color.Black.copy(alpha = 0.15f),
                Color.Transparent,
                Color.White.copy(alpha = 0.12f),
                Color.Transparent
            )
        )
    }

    val shape = RoundedCornerShape(topStart = 3.dp, bottomStart = 3.dp, topEnd = 9.dp, bottomEnd = 9.dp)

    Box(
        modifier = modifier
            .size(width = size.width, height = size.height)
            .shadow(elevation = 6.dp, shape = shape, spotColor = Color.Black.copy(alpha = 0.35f))
            .clip(shape)
            .background(
                Brush.linearGradient(
                    colors = listOf(baseColor, darkerShade)
                )
            )
    ) {
        // Book inner embossed frame
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp, vertical = 6.dp)
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.25f),
                    shape = RoundedCornerShape(4.dp)
                )
        )

        // Book Spine Shadow on left edge
        Box(
            modifier = Modifier
                .width(10.dp)
                .fillMaxHeight()
                .background(spineShadow)
        )

        // Front Cover Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 8.dp, top = 10.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Genre tag or small accent
            Text(
                text = book.genre.uppercase(),
                fontSize = (size.authorSize * 0.85f).sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White.copy(alpha = 0.75f),
                letterSpacing = 1.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Center Emblem Emoji
            Box(
                modifier = Modifier
                    .size((size.emojiSize * 1.3f).dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = book.coverEmoji,
                    fontSize = size.emojiSize.sp,
                    textAlign = TextAlign.Center
                )
            }

            // Title and Author
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = book.title,
                    fontSize = size.titleSize.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = (size.titleSize * 1.2f).sp
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = book.author,
                    fontSize = size.authorSize.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
