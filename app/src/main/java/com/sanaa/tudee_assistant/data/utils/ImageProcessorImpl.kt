package com.sanaa.tudee_assistant.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.graphics.scale
import com.sanaa.tudee_assistant.domain.ImageProcessor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImageProcessorImpl(private val context: Context) : ImageProcessor{

    @Throws(IOException::class)
    override suspend fun processImage(uri: Uri): Bitmap = withContext(Dispatchers.IO) {
        try {
            val originalBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
            } else {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            }
            val targetSize = (64 * context.resources.displayMetrics.density).toInt()
            originalBitmap.scale(targetSize, targetSize)

        } catch (e: Exception) {
            throw IOException("Failed to process image", e)
        }
    }

    @Throws(IOException::class)
    override suspend fun saveImageToInternalStorage(bitmap: Bitmap): String = withContext(Dispatchers.IO) {
        val directory = File(context.filesDir, STORAGE_CATEGORIES_FILE_DIR).apply {
            if (!exists()) mkdirs()
        }

        val file = File(directory, "${System.currentTimeMillis()}.png")

        FileOutputStream(file).use { outputStream ->
            if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)) {
                throw IOException("Failed to compress bitmap")
            }
            outputStream.flush()
        }

        file.absolutePath
    }
    companion object{
        const val STORAGE_CATEGORIES_FILE_DIR = "tudee/icon_category"
    }
}