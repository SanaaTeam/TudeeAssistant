package com.sanaa.tudee_assistant.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.route.CategoryTasksScreenRoute
import com.sanaa.tudee_assistant.presentation.route.HomeScreenRoute
import com.sanaa.tudee_assistant.presentation.route.MainScreenRoute
import com.sanaa.tudee_assistant.presentation.route.OnBoardingScreenRoute
import com.sanaa.tudee_assistant.presentation.screen.main.MainScreen

@Composable
fun TudeeApp() {
    var isDarkTheme by remember { mutableStateOf(false) }
    var statusBarColor by remember { mutableStateOf(Color.White) }

    TudeeTheme(isDark = isDarkTheme) {
        Scaffold(containerColor = statusBarColor) { innerPadding ->
            AppNavigation(
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                isDarkTheme = isDarkTheme,
                onChangeTheme = { isDarkTheme = !isDarkTheme },
                onStatusBarColor = { color -> statusBarColor = color }
            )
        }
    }
}

@Composable
private fun AppNavigation(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onChangeTheme: () -> Unit,
    onStatusBarColor: (Color) -> Unit,
) {
    val navHostController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = MainScreenRoute
    ) {
        composable<OnBoardingScreenRoute> {

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