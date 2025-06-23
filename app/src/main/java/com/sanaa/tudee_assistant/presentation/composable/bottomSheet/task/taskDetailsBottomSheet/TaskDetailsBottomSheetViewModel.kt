package com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task.taskDetailsBottomSheet

import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import com.sanaa.tudee_assistant.presentation.state.mapper.toState
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskDetailsBottomSheetViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService
): BaseViewModel<TaskUiState> (TaskUiState()){

    private val _selectedTaskCategoryImagePath = MutableStateFlow<String>("")
     val selectedTaskCategoryImagePath = _selectedTaskCategoryImagePath.asStateFlow()

    fun getSelectedTask(id: Int){
        viewModelScope.launch {
            _state.update {
                taskService.getTaskById(id).toState()
            }
            _selectedTaskCategoryImagePath.update {
                categoryService.getCategoryById(_state.value.categoryId).imagePath
            }
        }
    }



}