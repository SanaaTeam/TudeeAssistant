package com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sanaa.tudee_assistant.presentation.designSystem.component.BaseBottomSheet
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.screen.tasks.addEditTask.AddEditTaskContent
import com.sanaa.tudee_assistant.presentation.screen.tasks.addEditTask.AddEditTaskViewModel
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
    val showDatePickerDialog by viewModel.showDatePickerDialog.collectAsState()
    var isInitialized by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(isEditMode, taskToEdit, isInitialized) {
        if (!isInitialized) {
            if (!isEditMode) {
                viewModel.resetState()
                viewModel.loadCategoriesForNewTask()
            } else if (taskToEdit != null) {
                viewModel.loadTask(taskToEdit)
            }
            isInitialized = true
        }
    }

    LaunchedEffect(uiState.isOperationSuccessful, uiState.error) {
        if (uiState.isOperationSuccessful) {
            onSuccess()
            viewModel.resetState()
        }
        uiState.error?.let {
            onError(it)
        }
    }

    BaseBottomSheet(
        content = {
            Column(modifier = Modifier.fillMaxHeight(0.81f)) {
                AddEditTaskContent(
                    uiState = uiState,
                    categories = uiState.categories,
                    isEditMode = isEditMode,
                    showDatePickerDialog = showDatePickerDialog,
                    listener = viewModel,
                    onCancelClick = onDismiss
                )
            }
        },
        onDismiss = {
            onDismiss()
        }
    )
}