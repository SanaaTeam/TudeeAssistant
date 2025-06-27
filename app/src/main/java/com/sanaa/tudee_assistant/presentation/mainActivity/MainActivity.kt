package com.sanaa.tudee_assistant.presentation.mainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sanaa.tudee_assistant.presentation.app.TudeeApp
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    private val mainActivityViewModel: MainActivityViewModel = get<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {

        val mySplashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val state = mainActivityViewModel.state.collectAsStateWithLifecycle()
            val isSystemInDarkTheme = isSystemInDarkTheme()

            LaunchedEffect(state.value.isFirstLaunch) {
                if (state.value.isFirstLaunch) {
                    mainActivityViewModel.onSetDarkTheme(isSystemInDarkTheme)
                }
            }

            mySplashScreen.setKeepOnScreenCondition { state.value.isLoading }

            val isDark = if (state.value.isFirstLaunch) {
                isSystemInDarkTheme
            } else {
                state.value.isDarkTheme
            }

            if (!state.value.isLoading) {
                TudeeTheme(isDark) {
                    TudeeApp(
                        isFirstLaunch = state.value.isFirstLaunch,
                        isDarkTheme = isDark
                    )
                }
            }
        }
    }
}