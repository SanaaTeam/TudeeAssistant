package com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sanaa.tudee_assistant.presentation.designSystem.component.BaseBottomSheet
import com.sanaa.tudee_assistant.presentation.screen.tasks.addEditTask.AddEditTaskContent
import com.sanaa.tudee_assistant.presentation.screen.tasks.addEditTask.AddEditTaskViewModel
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    isEditMode: Boolean,
    taskToEdit: TaskUiState? = null,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit,
    onError: (String) -> Unit,
    viewModel: AddEditTaskViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var showBottomSheet by remember { mutableStateOf(true) }
    var showDatePickerDialog by remember { mutableStateOf(false) }


    LaunchedEffect(isEditMode, taskToEdit) {
        if (!isEditMode) {
            viewModel.resetState()
        }
        if (taskToEdit == null)
            viewModel.loadCategoriesForNewTask()
        else
            viewModel.loadTask(taskToEdit)
    }
    LaunchedEffect(uiState.isOperationSuccessful, uiState.error) {
        if (uiState.isOperationSuccessful) {
            showBottomSheet = false
            onSuccess()
            viewModel.resetState()
        }
    }

    if (showBottomSheet) {
        BaseBottomSheet(
            content = {
                Column(modifier = Modifier.fillMaxHeight(0.81f)) {
                    AddEditTaskContent(
                        uiState = uiState,
                        categories = uiState.categories,
                        isEditMode = isEditMode,
                        showDatePickerDialog = showDatePickerDialog,
                        onTitleChange = viewModel::onTitleChange,
                        onDescriptionChange = viewModel::onDescriptionChange,
                        onDateSelected = viewModel::onDateSelected,
                        onPrioritySelected = viewModel::onPrioritySelected,
                        onCategorySelected = viewModel::onCategorySelected,
                        onPrimaryButtonClick = if (isEditMode) viewModel::updateTask else viewModel::addTask,
                        onDismiss = onDismiss,
                        onDatePickerShow = { showDatePickerDialog = true },
                        onDatePickerDismiss = { showDatePickerDialog = false }
                    )
                }
            },
            onDismiss = onDismiss
        )
    }
}