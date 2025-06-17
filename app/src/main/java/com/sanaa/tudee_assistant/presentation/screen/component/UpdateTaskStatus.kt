package com.sanaa.tudee_assistant.presentation.screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.presentation.design_system.component.BaseBottomSheet
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTaskStatus(modifier: Modifier = Modifier) {
    BaseBottomSheet(
        onDismiss = {
            
        },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        content = {
            Column(
                modifier = Modifier.height(700.dp)
            ) {
                Text(
                    text = "Task Details",
                    modifier = Modifier,
                    style = Theme.textStyle.title.large,
                    color = Theme.color.title
                )
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewUpdateTaskStatus() {
    TudeeTheme {
        UpdateTaskStatus()
    }
}
