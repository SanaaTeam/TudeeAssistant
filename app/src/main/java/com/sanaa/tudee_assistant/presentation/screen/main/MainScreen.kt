package com.sanaa.tudee_assistant.presentation.screen.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.composable.TudeeScaffold
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
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    CompositionLocalProvider(LocalMainNavController provides navController) {
        TudeeScaffold (
        ){
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Theme.color.surface)
                    .navigationBarsPadding()
            ) {
                Box(Modifier.weight(1f)) {
                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                        modifier = Modifier.fillMaxSize(),
                        enterTransition = { fadeIn(tween()) },
                        exitTransition = { fadeOut(tween()) },
                    ) {
                        composable<HomeScreenRoute> {
                            HomeScreen()
                        }

                        composable<TasksScreenRoute> {
                            val route = it.toRoute<TasksScreenRoute>()
                            TasksScreen(screenRoute = route)
                        }

                        composable<CategoriesScreenRoute> {
                            CategoryScreen()
                        }
                    }
                }


                TudeeBottomNavBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(74.dp)
                ) {
                    TudeeBottomNavBarItem(
                        selected = currentDestination?.hasRoute(HomeScreenRoute::class) == true,
                        iconRes = R.drawable.home,
                        selectedIconRes = R.drawable.home_fill
                    ) {
                        navController.navigatePreservingState(HomeScreenRoute)
                    }

                    TudeeBottomNavBarItem(
                        selected = currentDestination?.hasRoute(TasksScreenRoute::class) == true,
                        iconRes = R.drawable.profile,
                        selectedIconRes = R.drawable.profile_fill
                    ) {
                        navController.navigatePreservingState(TasksScreenRoute())
                    }

                    TudeeBottomNavBarItem(
                        selected = currentDestination?.hasRoute(CategoriesScreenRoute::class) == true,
                        iconRes = R.drawable.menu,
                        selectedIconRes = R.drawable.menu_fill
                    ) {
                        navController.navigatePreservingState(CategoriesScreenRoute(TaskUiStatus.TODO))
                    }
                }
            }
        }
    }
}
