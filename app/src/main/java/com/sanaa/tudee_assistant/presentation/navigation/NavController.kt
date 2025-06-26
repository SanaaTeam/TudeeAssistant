package com.sanaa.tudee_assistant.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import com.sanaa.tudee_assistant.presentation.model.SnackBarState

val LocalAppNavController =
    staticCompositionLocalOf<NavHostController> {
        error("LocalAppNavController not provided. Did you forget to set it up with CompositionLocalProvider?")
    }
val LocalMainNavController = staticCompositionLocalOf<NavHostController> {
    error("LocalMainNavController not provided. Did you forget to set it up with CompositionLocalProvider?")
}

object AppNavigation {
    val app: NavHostController
        @Composable @ReadOnlyComposable get() = LocalAppNavController.current

    val mainScreen: NavHostController
        @Composable @ReadOnlyComposable get() = LocalMainNavController.current
}