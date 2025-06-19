package com.sanaa.tudee_assistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sanaa.tudee_assistant.data.utils.ThemeManager
import com.sanaa.tudee_assistant.presentation.TudeeApp
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeManager: ThemeManager= koinInject()

            var isDark by remember { mutableStateOf(false) }
            LaunchedEffect(themeManager) {
                themeManager.isDarkTheme.collect {
                    isDark = it
                }
            }

            TudeeTheme (isDark){
                TudeeApp()
            }
        }
    }
}
