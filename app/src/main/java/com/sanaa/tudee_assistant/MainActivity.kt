package com.sanaa.tudee_assistant

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
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.presentation.TudeeApp
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.screen.splash.SplashScreen
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var keepSplash by remember { mutableStateOf(true) }

            splashScreen.setKeepOnScreenCondition { keepSplash }

            val preferencesManager: PreferencesManager = koinInject()
            val isDark by preferencesManager.isDarkTheme.collectAsState(initial = false)
            val isFirstLaunch by preferencesManager.isFirstLaunch.collectAsState(initial = true)
            var showSplash by remember { mutableStateOf(true) }

            LaunchedEffect(Unit) {
                delay(1000)
                showSplash = false
                keepSplash = false
            }

            Log.d("SplashDebug", "Splash visible: $keepSplash")
            TudeeTheme(isDark) {
                if (showSplash) {
                    SplashScreen() // Your custom composable splash
                } else {
                    TudeeApp(isDark, preferencesManager, isFirstLaunch)
                }
            }
        }
    }
}
