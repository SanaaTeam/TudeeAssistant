package com.sanaa.tudee_assistant.presentation.state

import com.sanaa.tudee_assistant.presentation.model.TudeeStatus

data class SliderState(
    val title: String = "",
    val description: String = "",
    val status: TudeeStatus = TudeeStatus.OKAY,
)