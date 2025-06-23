package com.sanaa.tudee_assistant.presentation.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = staticCompositionLocalOf<NavHostController> { error("NavHostCon") }