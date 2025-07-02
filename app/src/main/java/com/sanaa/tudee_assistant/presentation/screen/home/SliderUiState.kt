package com.sanaa.tudee_assistant.presentation.screen.home

import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.model.TudeeUiStatus

data class SliderUiState(
    val title: String = "",
    val message: String = "",
    val robotImageRes: Int = R.drawable.robot,
    val status: TudeeUiStatus = TudeeUiStatus.POOR
)
