package com.sanaa.tudee_assistant.mainActivity

data class MainActivityUiState(
    val isLoading: Boolean = false,
    val isDarkTheme: Boolean = false,
    val isFirstLaunch: Boolean = true
)