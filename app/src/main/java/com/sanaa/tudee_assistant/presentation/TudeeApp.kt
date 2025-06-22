package com.sanaa.tudee_assistant.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.route.CategoryTasksScreenRoute
import com.sanaa.tudee_assistant.presentation.route.HomeScreenRoute
import com.sanaa.tudee_assistant.presentation.route.MainScreenRoute
import com.sanaa.tudee_assistant.presentation.route.OnBoardingScreenRoute
import com.sanaa.tudee_assistant.presentation.screen.categoryTask.CategoryTaskScreen
import com.sanaa.tudee_assistant.presentation.screen.main.MainScreen
import com.sanaa.tudee_assistant.presentation.screen.onBoarding.OnBoardingScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun TudeeApp(appViewModel: TudeeAppViewModel = koinViewModel()) {
    var statusBarColor by remember { mutableStateOf(Color.White) }
    val state by appViewModel.state.collectAsState()

    TudeeTheme(isDark = state.isDarkTheme) {
        Scaffold(containerColor = statusBarColor) { innerPadding ->
            AppNavigation(
                startDestination = if (state.isFirstLaunch) OnBoardingScreenRoute else MainScreenRoute,
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                onStatusBarColor = { color -> statusBarColor = color },
            )
        }
    }
}

@Composable
private fun AppNavigation(
    startDestination: Any,
    modifier: Modifier = Modifier,
    onStatusBarColor: (Color) -> Unit,
) {
    val navHostController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable<OnBoardingScreenRoute> {
            OnBoardingScreen(navController = navHostController)
        }

        composable<MainScreenRoute> {
            val screenNavController = rememberNavController()
            MainScreen(
                startDestination = HomeScreenRoute,
                screenNavController,
                onStatusBarColor,
                navHostController = navHostController
            )
        }

        composable<CategoryTasksScreenRoute> {
            CategoryTaskScreen(categoryId = it.toRoute<CategoryTasksScreenRoute>().id)
        }
    }
}