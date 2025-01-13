package com.excal.higherlower.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.excal.higherlower.R
import com.excal.higherlower.ui.theme.HIgherLowerTheme

@Composable
fun MovieContainer(
    modifier: Modifier = Modifier,
    imageUrl: String,
    placeholder: Painter = painterResource(id = R.drawable.poster_placeholder)
) {
    Box(modifier = Modifier, contentAlignment = Alignment.Center) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true).build(),
            contentDescription = null,
            modifier = modifier
                .padding()
                .background(Color.Black),
            placeholder = placeholder,
            contentScale = ContentScale.Crop,
            alpha = 0.6f,

            )

        Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Placeholder Movie Title",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color.White,
                modifier = Modifier
                    .width(150.dp)
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp

            )
            Text(text = "9999", color = Color.White, modifier = modifier.padding(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color.White
                )
                Text(
                    text = "6.9",
                    color = Color.White,
                    modifier = modifier.padding(8.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

            }

        }

    }
}

@Preview(showBackground = true)
@Composable
private fun MovieContainerPreview() {
    HIgherLowerTheme {
        MovieContainer(
            imageUrl = "https://image.tmdb.org/t/p/w500/d8Ryb8AunYAuycVKDp5HpdWPKgC.jpg",
            placeholder = painterResource(
                id = R.drawable.poster_placeholder
            ),

            )
    }

}