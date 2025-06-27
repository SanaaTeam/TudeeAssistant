package com.sanaa.tudee_assistant.presentation.navigation.util

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

fun <T : Any> NavHostController.navigateWithRestoreState(route: T) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id)
        launchSingleTop = true
        restoreState = true
    }
}