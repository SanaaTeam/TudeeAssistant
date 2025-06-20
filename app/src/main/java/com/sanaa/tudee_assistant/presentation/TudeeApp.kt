package com.sanaa.tudee_assistant.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.domain.PreferencesManager
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.route.CategoryTasksScreenRoute
import com.sanaa.tudee_assistant.presentation.route.HomeScreenRoute
import com.sanaa.tudee_assistant.presentation.route.MainScreenRoute
import com.sanaa.tudee_assistant.presentation.route.OnBoardingScreenRoute
import com.sanaa.tudee_assistant.presentation.route.SplashScreenRoute
import com.sanaa.tudee_assistant.presentation.route.TasksScreenRoute
import com.sanaa.tudee_assistant.presentation.screen.OnBoardingScreen
import com.sanaa.tudee_assistant.presentation.screen.home.HomeScreen
import com.sanaa.tudee_assistant.presentation.screen.main.MainScreen
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import com.sanaa.tudee_assistant.presentation.screen.main.composable
import com.sanaa.tudee_assistant.presentation.screen.taskScreen.TasksScreen

@Composable
fun TudeeApp(isDarkTheme: Boolean, preferencesManager: PreferencesManager, isFirstLaunch: Boolean) {
    val scope = rememberCoroutineScope()

    var statusBarColor by remember { mutableStateOf(Color.White) }



    TudeeTheme(isDark = isDarkTheme) {
        Scaffold(containerColor = statusBarColor) { innerPadding ->
            AppNavigation(
                startDestination = if (isFirstLaunch) OnBoardingScreenRoute else MainScreenRoute,
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                isDarkTheme = isDarkTheme,
                onChangeTheme = {
                    scope.launch {
                        preferencesManager.setDarkTheme(!isDarkTheme)
                    }
                },
                onStatusBarColor = { color -> statusBarColor = color },
                preferencesManager = preferencesManager
            )
        }
    }
}

@Composable
private fun AppNavigation(
    startDestination: Any,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onChangeTheme: () -> Unit,
    onStatusBarColor: (Color) -> Unit,
    preferencesManager: PreferencesManager,
) {
    val navHostController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable<SplashScreenRoute> {

        }

        composable<OnBoardingScreenRoute> {
            OnBoardingScreen(
                navController = navHostController,
                preferencesManager = preferencesManager
            )
        }

        composable<MainScreenRoute> {
            val screenNavController = rememberNavController()
            MainScreen(
                startDestination = HomeScreenRoute,
                screenNavController,
                isDarkTheme = isDarkTheme,
                onChangeTheme = onChangeTheme,
                onStatusBarColor
            )
        }

        composable<CategoryTasksScreenRoute> {

        }
    }
}