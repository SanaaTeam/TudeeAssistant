package com.sanaa.tudee_assistant.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.route.CategoriesScreenRoute
import com.sanaa.tudee_assistant.presentation.route.CategoryTasksScreenRoute
import com.sanaa.tudee_assistant.presentation.route.HomeScreenRoute
import com.sanaa.tudee_assistant.presentation.route.MainScreenRoute
import com.sanaa.tudee_assistant.presentation.route.OnBoardingScreenRoute
import com.sanaa.tudee_assistant.presentation.route.SplashScreenRoute
import com.sanaa.tudee_assistant.presentation.route.TasksScreenRoute
import com.sanaa.tudee_assistant.presentation.screen.main.MainScreen
import com.sanaa.tudee_assistant.presentation.screen.main.composable

@Composable
fun TudeeApp() {

    val navHostController = rememberNavController()
    NavHost(
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

                }

                composable(
                    route = TasksScreenRoute,
                    iconRes = R.drawable.profile,
                    selectedIconRes = R.drawable.profile_fill,
                ) {

                }

                composable(
                    route = CategoriesScreenRoute,
                    iconRes = R.drawable.menu,
                    selectedIconRes = R.drawable.menu_fill,
                ) {

                }
            }
        }

        composable<CategoryTasksScreenRoute> {

        }
    }
}