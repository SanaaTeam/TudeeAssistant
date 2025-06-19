package com.sanaa.tudee_assistant.presentation.screen.home

import androidx.lifecycle.ViewModel
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenUiState())
    val state = _state.asStateFlow()

    fun onAddTask() {

    }

    fun onTaskClick(categoryTaskState: TaskUiState) {

    }

    fun onOpenCategory(taskUiStatus: TaskUiStatus) {

    }
}