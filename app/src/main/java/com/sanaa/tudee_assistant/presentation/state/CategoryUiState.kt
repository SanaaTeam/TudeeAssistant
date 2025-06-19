package com.sanaa.tudee_assistant.presentation.state

data class CategoryUiState(
    val id: Int? = null,
    val name: String = "",
    val imagePath: String = "",
    val isDefault: Boolean = false,
)