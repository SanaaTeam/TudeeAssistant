package com.sanaa.tudee_assistant.presentation.shared

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import com.sanaa.tudee_assistant.presentation.model.SnackBarState

val LocalSnackBarState = compositionLocalOf {
    mutableStateOf(SnackBarState())
}
val LocalThemeState = compositionLocalOf {
   false
}