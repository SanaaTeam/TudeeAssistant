package com.sanaa.tudee_assistant.presentation.route

import kotlinx.serialization.Serializable

@Serializable
object SplashScreenRoute

@Serializable
object OnBoardingScreenRoute

@Serializable
object MainScreenRoute

@Serializable
data class CategoryTasksScreenRoute(
    val id: Int?
)
