package com.sanaa.tudee_assistant.presentation.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.sanaa.tudee_assistant.R

@Composable
fun CategoryThumbnail(
    modifier: Modifier = Modifier,
    imagePath: String,
) {
    AsyncImage(
        model = imagePath,
        contentDescription = "category image",
        modifier = modifier,
        error = painterResource(R.drawable.ic_not_found),
    )
}