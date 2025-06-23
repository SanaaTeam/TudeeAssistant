package com.sanaa.tudee_assistant.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnBoardingPageContentItem(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val imageRes: Int
)