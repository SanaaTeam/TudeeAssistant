package com.sanaa.tudee_assistant.presentation.screen.AddEditScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.presentation.composables.CustomDatePickerDialog
import com.sanaa.tudee_assistant.presentation.design_system.component.BaseBottomSheet
import com.sanaa.tudee_assistant.presentation.design_system.component.CategoryItem
import com.sanaa.tudee_assistant.presentation.design_system.component.CheckMarkContainer
import com.sanaa.tudee_assistant.presentation.design_system.component.PriorityTag
import com.sanaa.tudee_assistant.presentation.design_system.component.TudeeTextField
import com.sanaa.tudee_assistant.presentation.design_system.component.button.PrimaryButton
import com.sanaa.tudee_assistant.presentation.design_system.component.button.SecondaryButton
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.model.TaskPriority
import com.sanaa.tudee_assistant.presentation.utils.DateFormater
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTaskScreen(
    onDismiss: () -> Unit,
    onTaskAdded: () -> Unit,
    viewModel: AddTaskViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val categories by viewModel.categoryService.getCategories().collectAsState(initial = emptyList())

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.uiState.collectLatest { state ->
            if (state.error != null) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(state.error)
                }
            }
            if (state.taskAddedSuccessfully) {
                onTaskAdded()
                showBottomSheet = false
            }
        }
    }

    AddTaskContent(
        uiState = uiState,
        categories = categories,
        showBottomSheet = showBottomSheet,
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onDateSelected = viewModel::onDateSelected,
        onPrioritySelected = viewModel::onPrioritySelected,
        onCategorySelected = viewModel::onCategorySelected,
        onAddTask = viewModel::addTask,
        onDismiss = {
            showBottomSheet = false
            onDismiss()
        }
    )
}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskContent(
    uiState: AddTaskUiState,
    categories: List<Category>,
    showBottomSheet: Boolean,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onPrioritySelected: (Task.TaskPriority) -> Unit,
    onCategorySelected: (Category) -> Unit,
    onAddTask: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDatePickerDialog by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        BaseBottomSheet(
            content = {
                Column(
                    modifier = modifier
                        .fillMaxHeight(0.81f)
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .weight(1f)
                            .padding(Theme.dimension.medium)
                    ) {
                        Text(
                            text = stringResource(R.string.add_new_task),
                            modifier = Modifier.padding(bottom = Theme.dimension.medium),
                            fontSize = Theme.textStyle.title.large.fontSize,
                            color = Theme.color.title
                        )

                        TudeeTextField(
                            placeholder = stringResource(R.string.task_title),
                            value = uiState.title,
                            onValueChange = onTitleChange,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = Theme.dimension.medium),
                            icon = painterResource(R.drawable.task),
                        )
                        TudeeTextField(
                            placeholder = stringResource(R.string.description),
                            value = uiState.description,
                            onValueChange = onDescriptionChange,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(168.dp)
                                .padding(bottom = Theme.dimension.medium),
                        )

                            TudeeTextField(
                                modifier = Modifier.fillMaxWidth().padding(bottom = Theme.dimension.medium)
                                    .clickable { showDatePickerDialog = true },
                                placeholder = stringResource(R.string.date_placeholder),
                                value = uiState.selectedDate?.toString() ?: "",
                                onValueChange = {},
                                readOnly = true,
                                enabled = false,
                                icon = painterResource(R.drawable.calender),

                            )


                        Text(
                            text = stringResource(R.string.priority),
                            modifier = Modifier.padding(bottom = Theme.dimension.medium),
                            fontSize = Theme.textStyle.title.large.fontSize,
                            color = Theme.color.title
                        )
                        Row(
                            modifier = Modifier.padding(bottom = Theme.dimension.medium),
                            horizontalArrangement = Arrangement.spacedBy(Theme.dimension.small)
                        ) {
                            Task.TaskPriority.entries.forEach { priority ->
                                PriorityTag(
                                    priority = when (priority) {
                                        Task.TaskPriority.HIGH -> TaskPriority.HIGH
                                        Task.TaskPriority.MEDIUM -> TaskPriority.MEDIUM
                                        Task.TaskPriority.LOW -> TaskPriority.LOW
                                    },
                                    isSelected = uiState.selectedPriority == priority,
                                    onClick = { onPrioritySelected(priority) }
                                )
                            }
                        }

                        Text(
                            text = stringResource(R.string.category),
                            modifier = Modifier.padding(bottom = Theme.dimension.medium),
                            fontSize = Theme.textStyle.title.large.fontSize,
                            color = Theme.color.title
                        )
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 104.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 100.dp, max = 900.dp),
                            userScrollEnabled = false,
                            verticalArrangement = Arrangement.spacedBy(Theme.dimension.large),
                            horizontalArrangement = Arrangement.spacedBy(Theme.dimension.small)
                        ) {
                            items(categories.size) { index ->
                                val category = categories[index]
                                CategoryItem(
                                    category = category,
                                    onClick = { onCategorySelected(category) },
                                    topContent = {
                                        if (category == uiState.selectedCategory) {
                                            CheckMarkContainer()
                                        }
                                    },
                                    modifier = Modifier
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Theme.color.surfaceHigh)
                            .padding(
                                vertical = Theme.dimension.regular,
                                horizontal = Theme.dimension.medium
                            )
                    ) {
                        PrimaryButton(
                            label = stringResource(R.string.add),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = Theme.dimension.regular),
                            onClick = onAddTask,
                            enabled = uiState.isAddTaskButtonEnabled && !uiState.isLoading
                        )
                        SecondaryButton(
                            lable = stringResource(R.string.cancel),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = onDismiss
                        )
                    }
                }
            },
            onDismiss = onDismiss
        )
    }
    if (showDatePickerDialog) {
        CustomDatePickerDialog(
            onDateSelected = { millis ->
                millis?.let {
                    val dateTime = DateFormater.formatLongToDate(millis)
                    onDateSelected(dateTime)
                }
            },
            onDismiss = { showDatePickerDialog = false },
        )
    }

}

