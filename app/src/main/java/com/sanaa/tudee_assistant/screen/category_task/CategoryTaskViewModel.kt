package com.sanaa.tudee_assistant.screen.category_task

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoryTaskViewModel : ViewModel() {
    private val _state = MutableStateFlow(CategoryTaskUiState())
    val state = _state.asStateFlow()
}
