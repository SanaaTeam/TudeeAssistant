package com.sanaa.tudee_assistant.domain.service

import android.graphics.Bitmap
import android.net.Uri

interface ImageProcessor {
    suspend fun processImage(uri: Uri): Bitmap
    suspend fun saveImageToInternalStorage(bitmap: Bitmap): String
}