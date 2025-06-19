package com.sanaa.tudee_assistant.data.utils

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sanaa.tudee_assistant.R


@Composable
fun CategoryThumbnail(
    modifier: Modifier = Modifier,
    imagePath: String,
) {
    AsyncImage(
        model = imagePath ,
        contentDescription = "category image",
        modifier = modifier.size(32.dp).clip(CircleShape),
        error = painterResource(R.drawable.ic_not_found),
    )
}