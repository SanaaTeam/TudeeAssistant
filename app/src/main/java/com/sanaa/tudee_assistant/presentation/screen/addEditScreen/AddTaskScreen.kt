package com.sanaa.tudee_assistant.presentation.screen.addEditScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.component.BaseBottomSheet
import com.sanaa.tudee_assistant.presentation.design_system.component.SnackBar
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.model.SnackBarStatus
import com.sanaa.tudee_assistant.presentation.state.TaskUiModel

import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    isEditMode: Boolean,
    initialTask: TaskUiModel? = null,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit,
    onError: (String) -> Unit,
    viewModel: TaskFormViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val categories by viewModel.categoryService.getCategories().collectAsState(initial = emptyList())
    var showBottomSheet by remember { mutableStateOf(true) }

    LaunchedEffect(initialTask) {
        initialTask?.let { viewModel.loadTask(it) }
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
                    TaskForm(
                        title = uiState.taskUiModel.title,
                        description = uiState.taskUiModel.description ?: "",
                        dueDate = uiState.taskUiModel.dueDate,
                        selectedPriority = uiState.taskUiModel.priority,
                        selectedCategory = uiState.selectedCategory,
                        categories = categories,
                        screenTitle = stringResource(if (isEditMode) R.string.edit_task else R.string.add_new_task),
                        primaryButtonText = stringResource(if (isEditMode) R.string.save else R.string.add),
                        isLoading = uiState.isLoading,
                        isButtonEnabled = uiState.isButtonEnabled,
                        onTitleChange = viewModel::onTitleChange,
                        onDescriptionChange = viewModel::onDescriptionChange,
                        onDateSelected = viewModel::onDateSelected,
                        onPrioritySelected = viewModel::onPrioritySelected,
                        onCategorySelected = viewModel::onCategorySelected,
                        onPrimaryButtonClick = if (isEditMode) viewModel::updateTask else viewModel::addTask,
                        onDismiss = onDismiss
                    )
                }
            },
            onDismiss = onDismiss
        )
    }
}

@Composable
fun TudeeSnackBar(snackBarHostState: SnackbarHostState, isError: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding()
    ) {
        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = Theme.dimension.medium)
        ) { data ->
            SnackBar(
                message = data.visuals.message,
                snackBarStatus = if (isError) SnackBarStatus.ERROR else SnackBarStatus.SUCCESS
            )
        }
    }
}