package com.sanaa.tudee_assistant.presentation.screen.category

import com.sanaa.tudee_assistant.presentation.state.CategoryUiState

data class CategoryScreenUiState(
    val currentDateCategory: List<CategoryUiState> = emptyList(),
    val isLoading: Boolean = false,
)

