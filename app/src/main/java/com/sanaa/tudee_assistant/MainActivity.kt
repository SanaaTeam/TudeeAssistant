package com.sanaa.tudee_assistant

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.data.local.dto.CategoryLocalDto
import com.sanaa.tudee_assistant.data.utils.getAssetsImagePainter
import com.sanaa.tudee_assistant.data.utils.getDefaultCategories

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                var defaultCategories: List<CategoryLocalDto> by remember { mutableStateOf(listOf<CategoryLocalDto>()) }
                defaultCategories = getDefaultCategories()

                Log.d("test", "onCreate: category 0 path = ${defaultCategories[0].imagePath}")
                LazyColumn {

                    items(defaultCategories){
                        Image(
                            modifier = Modifier.size(32.dp),
                            painter = getAssetsImagePainter(LocalContext.current,it.imagePath),
                            contentDescription = null
                        )
                    }
                }


            }
        }
    }
}
