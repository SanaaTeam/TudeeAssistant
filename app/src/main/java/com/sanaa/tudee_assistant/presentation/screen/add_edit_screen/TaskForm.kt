package com.sanaa.tudee_assistant.presentation.screen.add_edit_screen

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.presentation.composable.CustomDatePickerDialog
import com.sanaa.tudee_assistant.presentation.design_system.component.CategoryItem
import com.sanaa.tudee_assistant.presentation.design_system.component.CheckMarkContainer
import com.sanaa.tudee_assistant.presentation.design_system.component.PriorityTag
import com.sanaa.tudee_assistant.presentation.design_system.component.TudeeTextField
import com.sanaa.tudee_assistant.presentation.design_system.component.button.PrimaryButton
import com.sanaa.tudee_assistant.presentation.design_system.component.button.SecondaryButton
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun TaskForm(
    title: String,
    description: String,
    dueDate: String?,
    selectedPriority: TaskUiPriority,
    selectedCategory: Category?,
    categories: List<Category>,
    screenTitle: String,
    primaryButtonText: String,
    isLoading: Boolean,
    isButtonEnabled: Boolean,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onPrioritySelected: (TaskUiPriority) -> Unit,
    onCategorySelected: (Category) -> Unit,
    onPrimaryButtonClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDatePickerDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = screenTitle,
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
                    value = title,
                    onValueChange = onTitleChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Theme.dimension.medium),
                    icon = painterResource(R.drawable.task),
                )
                
                TudeeTextField(
                    placeholder = stringResource(R.string.description),
                    value = description,
                    onValueChange = onDescriptionChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(168.dp)
                        .padding(bottom = Theme.dimension.medium),
                )

                TudeeTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Theme.dimension.medium)
                        .clickable { showDatePickerDialog = true },
                    placeholder = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toString(),
                    value = dueDate ?: "",
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
                            isSelected = selectedPriority == priority,
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
                                if (category == selectedCategory) {
                                    CheckMarkContainer()
                                }
                            },
                            modifier = Modifier
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
                label = primaryButtonText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Theme.dimension.regular),
                onClick = onPrimaryButtonClick,
                enabled = isButtonEnabled && !isLoading
            )
            SecondaryButton(
                lable = stringResource(R.string.cancel),
                modifier = Modifier.fillMaxWidth(),
                onClick = onDismiss
            )
        }
    }

    if (showDatePickerDialog) {
        CustomDatePickerDialog(
            onDateSelected = { millis ->
                millis?.let {
                    val dateTime = LocalDate.fromEpochDays((millis / (24 * 60 * 60 * 1000)).toInt())
                    onDateSelected(dateTime)
                }
            },
            onDismiss = { showDatePickerDialog = false },
        )
    }
} 