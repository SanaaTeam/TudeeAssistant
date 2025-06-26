package com.sanaa.tudee_assistant.presentation.screen.onBoarding

import com.sanaa.tudee_assistant.presentation.model.OnBoardingPageContentItem

data class OnBoardingScreenUiState(
    val isDarkTheme: Boolean = false,
    val pageList: List<OnBoardingPageContentItem> =emptyList(),
    val currentPageIndex: Int = 0,
    val isSkipable: Boolean = false,
)