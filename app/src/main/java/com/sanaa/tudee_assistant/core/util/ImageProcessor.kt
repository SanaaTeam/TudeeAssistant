package com.sanaa.tudee_assistant.core.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.core.graphics.scale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImageProcessor(private val context: Context) {

    suspend fun processImage(uri: Uri): Bitmap = withContext(Dispatchers.IO) {
        val originalBitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        val scale = context.resources.displayMetrics.density
        val sizePx = (32 * scale).toInt()
        originalBitmap.scale(sizePx, sizePx)
    }

    fun saveImageToInternalStorage(bitmap: Bitmap): String {
        val directoryName = "tudee/icon_category"
        val fileName = "${System.currentTimeMillis()}.png"

        val directory = File(context.filesDir, directoryName)

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, fileName)

        return try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }

}
