package com.sanaa.tudee_assistant.presentation.navigation.util

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

fun <T : Any> NavHostController.navigatePreservingState(route: T) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}