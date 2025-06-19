package com.sanaa.tudee_assistant.presentation.screen.main

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sanaa.tudee_assistant.presentation.design_system.component.TudeeBottomNavBar
import com.sanaa.tudee_assistant.presentation.design_system.component.TudeeBottomNavBarItem

internal var navItems = mutableSetOf<NavItem>()

internal data class NavItem(
    val route: Any,
    @DrawableRes val iconRes: Int,
    @DrawableRes val selectedIconRes: Int,
)

@Composable
fun MainScreen(startDestination: Any, builder: NavGraphBuilder.() -> Unit) {
    val screenNavController = rememberNavController()
    val navBackStackEntry by screenNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Column {
        NavHost(
            modifier = Modifier.weight(1f),
            navController = screenNavController,
            startDestination = startDestination,
            builder = builder
        )

        TudeeBottomNavBar {
            navItems.forEach {
                TudeeBottomNavBarItem(
                    selected = currentDestination?.hasRoute(it.route::class) == true,
                    iconRes = it.iconRes,
                    selectedIconRes = it.selectedIconRes
                ) {
                    screenNavController.navigateTo(it.route)

                }
            }
        }
    }
}

fun NavGraphBuilder.composable(
    route: Any,
    iconRes: Int,
    selectedIconRes: Int,
    content: @Composable () -> Unit,
) {
    navItems.add(NavItem(route, iconRes, selectedIconRes))
    composable(route::class) {
        content()
    }
}

internal fun <T : Any> NavHostController.navigateTo(route: T) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
