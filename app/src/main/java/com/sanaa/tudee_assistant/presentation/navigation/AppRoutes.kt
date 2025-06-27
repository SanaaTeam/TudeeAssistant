package com.sanaa.tudee_assistant.presentation.navigation

import kotlinx.serialization.Serializable

open class AppRoute

@Serializable
object OnBoardingScreenRoute : AppRoute()

@Serializable
object MainScreenRoute : AppRoute()

@Serializable
data class CategoryTasksScreenRoute(val id: Int?) : AppRoute()
