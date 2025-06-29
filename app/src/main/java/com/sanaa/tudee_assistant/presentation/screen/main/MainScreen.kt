package com.sanaa.tudee_assistant.presentation.screen.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.component.TudeeBottomNavBar
import com.sanaa.tudee_assistant.presentation.designSystem.component.TudeeBottomNavBarItem
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.navigation.CategoriesScreenRoute
import com.sanaa.tudee_assistant.presentation.navigation.HomeScreenRoute
import com.sanaa.tudee_assistant.presentation.navigation.LocalMainNavController
import com.sanaa.tudee_assistant.presentation.navigation.TasksScreenRoute
import com.sanaa.tudee_assistant.presentation.navigation.util.navigatePreservingState
import com.sanaa.tudee_assistant.presentation.screen.category.CategoryScreen
import com.sanaa.tudee_assistant.presentation.screen.home.HomeScreen
import com.sanaa.tudee_assistant.presentation.screen.tasks.TasksScreen

@Composable
fun MainScreen(
    startDestination: Any,
    modifier: Modifier = Modifier,
) {
    val screenNavController = rememberNavController()
    val navBackStackEntry by screenNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    val systemUiController = rememberSystemUiController()
    val color = Theme.color.surfaceHigh
    SideEffect {
        systemUiController.setNavigationBarColor(
            color = color, darkIcons = true
        )
    }

    Column(modifier = modifier) {
        CompositionLocalProvider(LocalMainNavController provides screenNavController) {
            NavHost(
                modifier = Modifier.weight(1f),
                navController = screenNavController,
                startDestination = startDestination,
                enterTransition = { fadeIn(tween()) },
                exitTransition = { fadeOut(tween()) },
            ) {
                composable<HomeScreenRoute> {
                    HomeScreen()
                }

                composable<TasksScreenRoute> {
                    TasksScreen(screenRoute = it.toRoute<TasksScreenRoute>())
                }

                composable<CategoriesScreenRoute> {
                    CategoryScreen()
                }
            }
        }

        TudeeBottomNavBar {
            TudeeBottomNavBarItem(
                selected = currentDestination?.hasRoute(HomeScreenRoute::class) == true,
                iconRes = R.drawable.home,
                selectedIconRes = R.drawable.home_fill,
            ) {
                screenNavController.navigatePreservingState(HomeScreenRoute)
            }
            TudeeBottomNavBarItem(
                selected = currentDestination?.hasRoute(TasksScreenRoute::class) == true,
                iconRes = R.drawable.profile,
                selectedIconRes = R.drawable.profile_fill,
            ) {
                screenNavController.navigatePreservingState(TasksScreenRoute())
            }
            TudeeBottomNavBarItem(
                selected = currentDestination?.hasRoute(CategoriesScreenRoute::class) == true,
                iconRes = R.drawable.menu,
                selectedIconRes = R.drawable.menu_fill,
            ) {
                screenNavController.navigatePreservingState(CategoriesScreenRoute(TaskUiStatus.TODO))
            }
        }
    }
}
