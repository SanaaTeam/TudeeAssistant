package com.sanaa.tudee_assistant.presentation.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.component.TudeeBottomNavBar
import com.sanaa.tudee_assistant.presentation.design_system.component.TudeeBottomNavBarItem
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.route.CategoriesScreenRoute
import com.sanaa.tudee_assistant.presentation.route.HomeScreenRoute
import com.sanaa.tudee_assistant.presentation.route.TasksScreenRoute
import com.sanaa.tudee_assistant.presentation.screen.category.CategoryScreen
import com.sanaa.tudee_assistant.presentation.screen.home.HomeScreen
import com.sanaa.tudee_assistant.presentation.screen.taskScreen.TasksScreen

@Composable
fun MainScreen(
    startDestination: Any,
    screenNavController: NavHostController,
    isDarkTheme: Boolean,
    onChangeTheme: () -> Unit,
    onStatusBarColor: (Color) -> Unit,
    navHostController: NavHostController,
) {
    val navBackStackEntry by screenNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Column {
        NavHost(
            modifier = Modifier.weight(1f),
            navController = screenNavController,
            startDestination = startDestination,
        ) {
            composable<HomeScreenRoute> {
                onStatusBarColor(Theme.color.primary)

                HomeScreen()
            }

            composable<TasksScreenRoute> {
                onStatusBarColor(Theme.color.surface)
                TasksScreen(screenRoute = it.toRoute<TasksScreenRoute>())
            }

            composable<CategoriesScreenRoute> {
                onStatusBarColor(Theme.color.surfaceHigh)
                CategoryScreen(screenNavController = navHostController)
            }
        }

        TudeeBottomNavBar {
            TudeeBottomNavBarItem(
                selected = currentDestination?.hasRoute(HomeScreenRoute::class) == true,
                iconRes = R.drawable.home,
                selectedIconRes = R.drawable.home_fill,
            ) {
                screenNavController.navigateTo(HomeScreenRoute)
            }
            TudeeBottomNavBarItem(
                selected = currentDestination?.hasRoute(TasksScreenRoute::class) == true,
                iconRes = R.drawable.profile,
                selectedIconRes = R.drawable.profile_fill,
            ) {
                screenNavController.navigateTo(TasksScreenRoute())
            }
            TudeeBottomNavBarItem(
                selected = currentDestination?.hasRoute(CategoriesScreenRoute::class) == true,
                iconRes = R.drawable.menu,
                selectedIconRes = R.drawable.menu_fill,
            ) {
                screenNavController.navigateTo(CategoriesScreenRoute(TaskUiStatus.TODO))
            }
        }
    }
}

fun <T : Any> NavHostController.navigateTo(route: T) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
