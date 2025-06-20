package com.sanaa.tudee_assistant

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sanaa.tudee_assistant.domain.ThemeManager
import com.sanaa.tudee_assistant.presentation.TudeeApp
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.screen.SplashScreen
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    private var keepSplashOn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            installSplashScreen().setKeepOnScreenCondition { keepSplashOn }
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeManager: ThemeManager = koinInject()
            val isDark by themeManager.isDarkTheme.collectAsState(initial = false)
            var showSplash by remember { mutableStateOf(true) }

            LaunchedEffect(Unit) {
                delay(1000)
                showSplash = false
                keepSplashOn = false
            }
            Log.d("isDark Test", "onCreate: is dark value is : $isDark ")
            TudeeTheme(isDark) {
                if (showSplash) {
                    SplashScreen()
                } else {
                    TudeeApp(isDark)
                }
            }
        }
    }
}
