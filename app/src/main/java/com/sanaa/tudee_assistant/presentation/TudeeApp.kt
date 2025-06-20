package com.sanaa.tudee_assistant.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import com.sanaa.navigationtry.route.CategoriesScreenRoute
import com.sanaa.navigationtry.route.CategoryTasksScreenRoute
import com.sanaa.navigationtry.route.HomeScreenRoute
import com.sanaa.navigationtry.route.MainScreenRoute
import com.sanaa.navigationtry.route.OnBoardingScreenRoute
import com.sanaa.navigationtry.route.SplashScreenRoute
import com.sanaa.navigationtry.route.TasksScreenRoute
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.domain.ThemeManager
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.screen.home.HomeScreen
import com.sanaa.tudee_assistant.presentation.screen.main.MainScreen
import com.sanaa.tudee_assistant.presentation.screen.main.composable
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun TudeeApp(isDarkTheme: Boolean) {
    val themeManager: ThemeManager = koinInject()
    val scope = rememberCoroutineScope()

    var statusBarColor by remember { mutableStateOf(Color.White) }

    TudeeTheme(isDark = isDarkTheme) {
        Scaffold(containerColor = statusBarColor) { innerPadding ->
            AppNavigation(
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                isDarkTheme = isDarkTheme,
                onChangeTheme = {
                    scope.launch {
                        themeManager.setDarkTheme(!isDarkTheme )
                    }
                                },
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
        composable<SplashScreenRoute> {

        }

        composable<OnBoardingScreenRoute> {

        }

        composable<MainScreenRoute> {
            MainScreen(startDestination = HomeScreenRoute) {
                composable(
                    route = HomeScreenRoute,
                    iconRes = R.drawable.home,
                    selectedIconRes = R.drawable.home_fill,
                ) {
                    onStatusBarColor(Theme.color.primary)

                    HomeScreen(
                        isDark = isDarkTheme,
                        onChangeTheme = onChangeTheme,
                    )
                }

                composable(
                    route = TasksScreenRoute,
                    iconRes = R.drawable.profile,
                    selectedIconRes = R.drawable.profile_fill,
                ) {
                    onStatusBarColor(Theme.color.surface)

                }

                composable(
                    route = CategoriesScreenRoute,
                    iconRes = R.drawable.menu,
                    selectedIconRes = R.drawable.menu_fill,
                ) {
                    onStatusBarColor(Theme.color.surface)

                }
            }
        }

        composable<CategoryTasksScreenRoute> {

        }
    }
}