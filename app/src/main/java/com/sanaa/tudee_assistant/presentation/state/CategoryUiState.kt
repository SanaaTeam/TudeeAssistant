package com.sanaa.tudee_assistant.presentation.state

data class CategoryUiState(
    val id: Int = 0,
    val name: String = "",
    val imagePath: String = "",
    val isDefault: Boolean = false,
    val tasksCount: Int = 0,
)