package com.pamn.museo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ImageSlider() {
    Column() {
        val images = listOf(
            "https://alanxelmundo.com/wp-content/uploads/2023/05/David4.jpg",
            "https://i0.wp.com/culturacolectiva.com/wp-content/uploads/images/LKBROP3EP5CTDAPQ6KECF6QC3U.jpg?ssl=1",

        )

        ImageSlider(images)
    }
}

@Composable
fun NetworkImage(url: String, contentDescription: String?, width: Int, height: Int) {
    val painter: Painter = rememberImagePainter(url)
    Image(
        painter = painter,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
    )
}

@Composable
fun ImageSlider(images: List<Any>) {
    var currentImageIndex by remember { mutableStateOf(0) }
    var isAnimating by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {

        Box(modifier = Modifier.weight(1f).height(100.dp)
            .fillMaxWidth().padding(16.dp)) {
            // Scrollable Row of Cards
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(images) { index, image ->
                    Card(
                        modifier = Modifier
                            .width(300.dp)
                            .height(200.dp)
                            .clickable {
                                if (index != currentImageIndex && !isAnimating) {
                                    isAnimating = true
                                    coroutineScope.launch {
                                        val delayMillis = 500L
                                        // Wait for the card to change color before animating
                                        delay(delayMillis / 2)
                                        currentImageIndex = index
                                        delay(delayMillis)
                                        isAnimating = false
                                    }
                                }
                            },
                        elevation = 4.dp
                    ) {
                        NetworkImage(
                            contentDescription = "",
                            url = image as String,
                            width = 300,
                            height = 200
                        )
                    }
                }

            }

        }
        // Dots Indicator
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            for (i in images.indices) {
                Surface(
                    modifier = Modifier
                        .size(10.dp)
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(
                            if (currentImageIndex == i) MaterialTheme.colors.primary
                            else Color.Black
                        )
                        .clickable {
                            if (i != currentImageIndex && !isAnimating) {
                                isAnimating = true
                                coroutineScope.launch {
                                    val delayMillis = 500L
                                    // Wait for the dot to change color before animating
                                    delay(delayMillis / 2)
                                    currentImageIndex = i
                                    delay(delayMillis)
                                    isAnimating = false
                                }
                            }
                        },
                    color = Color.Transparent
                ) {

                }
            }
        }
    }

    LaunchedEffect(currentImageIndex) {
        while (true) {
            delay(5000L)
            if (!isAnimating) {
                val nextIndex = (currentImageIndex + 1) % images.size
                currentImageIndex = nextIndex
            }
        }
    }
}