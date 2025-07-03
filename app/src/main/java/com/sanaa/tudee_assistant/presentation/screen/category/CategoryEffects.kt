package com.sanaa.tudee_assistant.presentation.screen.category

sealed class CategoryEffects {
    data class NavigateToCategoryTasks(val categoryId: Int) : CategoryEffects()
}