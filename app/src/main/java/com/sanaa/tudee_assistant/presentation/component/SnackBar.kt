package com.sanaa.tudee_assistant.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.Status

@Composable
fun SnackBar(
    status: Status = Status.SUCCESS,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = Theme.color.surfaceHigh, shape = RoundedCornerShape(16.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (status == Status.ERROR) Theme.color.errorVariant else Theme.color.greenVariant,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(9.25.dp)
        ) {
            Image(
                painter = painterResource(
                    id = if (status == Status.ERROR) R.drawable.snack_bar_error else R.drawable.snack_bar_success
                ),
                contentDescription = description,
                modifier = Modifier.size(24.dp),

                )
        }

        Text(
            text = description,
            color = Theme.color.body,
            style = Theme.textStyle.body.medium,
            modifier = Modifier.padding(start = 12.dp)

        )
    }
}

@Preview(name = "Dark Theme")
@Composable
fun SnackBarDarkPreview() {
    TudeeTheme(isDarkTheme = true) {
        SnackBar(description = "Success")
    }
}

@Preview(name = "Light Theme")
@Composable
fun SnackBarLightPreview() {
    TudeeTheme(isDarkTheme = false) {
        SnackBar(Status.ERROR, description = "Something went wrong")
    }
}