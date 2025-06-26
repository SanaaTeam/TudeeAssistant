package com.sanaa.tudee_assistant.presentation.mainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sanaa.tudee_assistant.presentation.app.TudeeApp
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val mySplashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainActivityViewModel: MainActivityViewModel =
                koinViewModel<MainActivityViewModel>()

            var state = mainActivityViewModel.state.collectAsStateWithLifecycle()

            mySplashScreen.setKeepOnScreenCondition { state.value.isLoading }

            if (!state.value.isLoading) {
                TudeeTheme(state.value.isDarkTheme) {
                    TudeeApp(
                        isFirstLaunch = state.value.isFirstLaunch,
                        isDarkTheme = state.value.isDarkTheme
                    )
                }
            }
        }
    }
}