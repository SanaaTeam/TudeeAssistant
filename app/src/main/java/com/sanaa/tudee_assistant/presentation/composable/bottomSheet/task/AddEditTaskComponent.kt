package com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sanaa.tudee_assistant.presentation.designSystem.component.BaseBottomSheet
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.screen.tasks.addEditTask.AddEditTaskContent
import com.sanaa.tudee_assistant.presentation.screen.tasks.addEditTask.AddEditTaskViewModel
import kotlinx.datetime.LocalDate
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    isEditMode: Boolean,
    taskToEdit: TaskUiState? = null,
    initialDate: LocalDate? = null,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit,
    onError: (String) -> Unit,
    viewModel: AddEditTaskViewModel = koinViewModel(),
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val showDatePickerDialog by viewModel.showDatePickerDialog.collectAsState()

    LaunchedEffect(isEditMode, taskToEdit, initialDate) {
        viewModel.initTaskState(isEditMode, taskToEdit, initialDate)
    }
    LaunchedEffect(uiState.isOperationSuccessful, uiState.error) { // Corrected lines
        if (uiState.isOperationSuccessful) {
            onSuccess()
            viewModel.resetState()
            onDismiss()
        }
        uiState.error?.let {
            onError(it)
            onDismiss()
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