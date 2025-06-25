package com.sanaa.tudee_assistant.presentation.screen.tasks.addEditTask

import com.sanaa.tudee_assistant.presentation.model.CategoryUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority

interface AddEditTaskListener {
    fun onTitleChange(title: String)
    fun onDescriptionChange(description: String)
    fun onDateSelected(date: kotlinx.datetime.LocalDate)
    fun onPrioritySelected(priority: TaskUiPriority)
    fun onCategorySelected(category: CategoryUiState)
    fun onPrimaryButtonClick()
    fun onDatePickerShow()
    fun onDatePickerDismiss()
}