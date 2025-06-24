package com.sanaa.tudee_assistant.presentation.screen.tasks.addEditTask

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.composable.CustomDatePickerDialog
import com.sanaa.tudee_assistant.presentation.designSystem.component.CategoryItem
import com.sanaa.tudee_assistant.presentation.designSystem.component.CheckMarkContainer
import com.sanaa.tudee_assistant.presentation.designSystem.component.PriorityTag
import com.sanaa.tudee_assistant.presentation.designSystem.component.TudeeTextField
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.PrimaryButton
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.SecondaryButton
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState
import com.sanaa.tudee_assistant.presentation.utils.DateFormater
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

@Composable
fun AddEditTaskContent(
    uiState: AddTaskUiState,
    categories: List<CategoryUiState>,
    isEditMode: Boolean,
    showDatePickerDialog: Boolean,
    listener: AddEditTaskListener,
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(if (isEditMode) R.string.edit_task else R.string.add_new_task),
                modifier = Modifier.padding(
                    start = Theme.dimension.medium,
                    top = Theme.dimension.medium,
                    bottom = Theme.dimension.medium
                ),
                fontSize = Theme.textStyle.title.large.fontSize,
                color = Theme.color.title
            )

            Column(
                modifier = Modifier.padding(horizontal = Theme.dimension.medium)
            ) {
                TudeeTextField(
                    placeholder = stringResource(R.string.task_title),
                    value = uiState.taskUiState.title,
                    onValueChange = listener::onTitleChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Theme.dimension.medium),
                    icon = painterResource(R.drawable.task),
                )

                TudeeTextField(
                    placeholder = stringResource(R.string.description),
                    value = uiState.taskUiState.description ?: "",
                    onValueChange = listener::onDescriptionChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(168.dp)
                        .padding(bottom = Theme.dimension.medium),
                )

                TudeeTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Theme.dimension.medium)
                        .clip(shape = RoundedCornerShape(Theme.dimension.medium))
                        .clickable { listener.onDatePickerShow() },
                    placeholder = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()).date.toString(),
                    value = uiState.taskUiState.dueDate,
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
                    TaskUiPriority.entries.forEach { priority ->
                        PriorityTag(
                            priority = priority,
                            isSelected = uiState.taskUiState.priority == priority,
                            onClick = { listener.onPrioritySelected(priority) }
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
                    items(categories) { category ->
                        CategoryItem(
                            category = category,
                            modifier = Modifier
                                .padding(bottom = Theme.dimension.regular),
                            onClick = { listener.onCategorySelected(category) },
                            topContent = {
                                if (category == uiState.selectedCategory) {
                                    CheckMarkContainer()
                                }
                            },
                        )
                    }
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
                label = stringResource(if (isEditMode) R.string.save else R.string.add),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Theme.dimension.regular),
                isLoading = uiState.isLoading,
                onClick = listener::onPrimaryButtonClick,
                enabled = uiState.isButtonEnabled && !uiState.isLoading
            )
            SecondaryButton(
                lable = stringResource(R.string.cancel),
                modifier = Modifier.fillMaxWidth(),
                onClick = onCancelClick
            )
        }
    }

    if (showDatePickerDialog) {
        CustomDatePickerDialog(
            onDateSelected = { selectedDateMillis: Long? ->
                selectedDateMillis?.let {
                    val date = DateFormater.formatLongToDate(selectedDateMillis)
                    listener.onDateSelected(date)
                }
            },
            onDismiss = listener::onDatePickerDismiss,
            minDateMillis = Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .date
                .atStartOfDayIn(TimeZone.currentSystemDefault())
                .toEpochMilliseconds()

        )
    }
}