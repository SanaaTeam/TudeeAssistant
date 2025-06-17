package com.sanaa.tudee_assistant.data.local.utils

import android.content.Context
import androidx.compose.runtime.Composable
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.sanaa.tudee_assistant.R

@Composable
fun getAssetsImagePainter(context: Context, imagePath: String): AsyncImagePainter =
    rememberAsyncImagePainter(
        ImageRequest.Builder(context)
            .data(imagePath)
            .error(R.drawable.ic_not_found) // Show if load fails
            .build()
    )