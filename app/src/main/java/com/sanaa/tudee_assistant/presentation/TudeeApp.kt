package com.sanaa.tudee_assistant.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sanaa.navigationtry.route.CategoriesScreenRoute
import com.sanaa.navigationtry.route.CategoryTasksRoute
import com.sanaa.navigationtry.route.HomeScreenRoute
import com.sanaa.navigationtry.route.MainScreenRoute
import com.sanaa.navigationtry.route.OnBoardingScreenRoute
import com.sanaa.navigationtry.route.SplashScreenRoute
import com.sanaa.navigationtry.route.TasksRoute
import com.sanaa.tudee_assistant.R
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
                    route = TasksRoute,
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

        composable<CategoryTasksRoute> {

        }
    }
}