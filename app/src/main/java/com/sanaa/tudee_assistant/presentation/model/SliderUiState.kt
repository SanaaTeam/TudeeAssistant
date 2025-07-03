package com.sanaa.tudee_assistant.presentation.model

import com.sanaa.tudee_assistant.R

data class SliderUiState(
    val title: String = "",
    val message: String = "",
    val robotImageRes: Int = R.drawable.robot,
    val status: TudeeUiStatus = TudeeUiStatus.POOR
)
