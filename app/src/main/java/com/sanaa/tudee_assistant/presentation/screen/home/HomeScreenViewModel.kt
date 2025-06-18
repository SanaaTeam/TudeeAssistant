package com.sanaa.tudee_assistant.presentation.screen.home

import androidx.lifecycle.ViewModel
import com.sanaa.tudee_assistant.presentation.model.TaskStatus
import com.sanaa.tudee_assistant.presentation.state.CategoryTaskState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenUiState())
    val state = _state.asStateFlow()

    fun onAddTask() {

    }

    fun onTaskClick(categoryTaskState: CategoryTaskState) {

    }

    fun onOpenCategory(taskStatus: TaskStatus) {

    }
}