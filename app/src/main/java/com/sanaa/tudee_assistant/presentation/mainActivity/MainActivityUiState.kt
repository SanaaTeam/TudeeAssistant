package com.sanaa.tudee_assistant.presentation.mainActivity

data class MainActivityUiState(
    val isLoading: Boolean = false,
    val isDarkTheme: Boolean = false,
    val isFirstLaunch: Boolean = true
)