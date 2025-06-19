package com.sanaa.tudee_assistant.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.component.button.TextButton
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.utils.DateFormater
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    modifier: Modifier = Modifier,
    onDateSelected: (Long?) -> Unit = {},
    onDismiss: () -> Unit = {},
    initialSelectedDate: LocalDate? = null,
) {
    val initialSelectedDateMillis = DateFormater.localDateToEpochMillis(initialSelectedDate)
    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = initialSelectedDateMillis)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis!!)
                    onDismiss()
                },
                label = stringResource(R.string.ok),
                modifier = modifier.padding(vertical = 20.dp, horizontal = 28.dp)
            )
        },
        dismissButton = {
            Row(
                modifier = Modifier.width(250.dp),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                TextButton(
                    onClick = { datePickerState.selectedDateMillis = null },
                    label = stringResource(R.string.clear),
                    modifier = Modifier.padding(vertical = 20.dp)
                )

                TextButton(
                    onClick = onDismiss,
                    label = stringResource(R.string.cancel),
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }
        },
        colors = DatePickerDefaults.colors(
            selectedDayContainerColor = Theme.color.primary,
            containerColor = Theme.color.surface,
            titleContentColor = Theme.color.title,
            headlineContentColor = Theme.color.title,
            weekdayContentColor = Theme.color.title,
            subheadContentColor = Color.Red,
            yearContentColor = Theme.color.body,
            currentYearContentColor = Theme.color.onPrimary,
            selectedYearContainerColor = Theme.color.primary,
            selectedYearContentColor = Theme.color.onPrimary,
            dayContentColor = Theme.color.title,
            selectedDayContentColor = Theme.color.onPrimary,
            todayContentColor = Theme.color.primary,
            todayDateBorderColor = Theme.color.primary,
            navigationContentColor = Theme.color.title,
            dividerColor = Theme.color.stroke,
        )
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = true,
            colors = DatePickerDefaults.colors(
                selectedDayContainerColor = Theme.color.primary,
                containerColor = Theme.color.surface,
                titleContentColor = Theme.color.title,
                headlineContentColor = Theme.color.title,
                weekdayContentColor = Theme.color.title,
                subheadContentColor = Color.Red,
                yearContentColor = Theme.color.body,
                currentYearContentColor = Theme.color.onPrimary,
                selectedYearContainerColor = Theme.color.primary,
                selectedYearContentColor = Theme.color.onPrimary,
                dayContentColor = Theme.color.title,
                selectedDayContentColor = Theme.color.onPrimary,
                todayContentColor = Theme.color.primary,
                todayDateBorderColor = Theme.color.primary,
                navigationContentColor = Theme.color.title,
                dividerColor = Theme.color.stroke,
            )
        )
    }

}

@Preview(widthDp = 360, locale = "ar")
@Composable
private fun CustomDatePickerDialogPreview() {

    var selectedDateText by remember { mutableStateOf("No Date") }
    var showDialog by remember { mutableStateOf(true) }

    TudeeTheme(isDarkTheme = true) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surface),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = selectedDateText,
                color = Theme.color.primary
            )
            if (showDialog) {
                CustomDatePickerDialog(
                    onDateSelected = { selectedDateMillis: Long? ->
                        selectedDateMillis?.let {
                            selectedDateText = DateFormater.formatLongToDate(it).toString()
                        }
                    },
                    onDismiss = { showDialog = false },
                )
            }
        }
    }
}