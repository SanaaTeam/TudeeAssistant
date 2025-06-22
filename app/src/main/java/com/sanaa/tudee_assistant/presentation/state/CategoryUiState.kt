package com.sanaa.tudee_assistant.presentation.state

data class CategoryUiState(
    val id: Int,
    val name: String,
    val imagePath: String,
    val isDefault: Boolean,
    val tasksCount: Int,
)