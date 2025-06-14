package com.sanaa.tudee_assistant.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.Status

@Composable
fun StatueImage(imageSize: Dp, status: Status) {
    Image(
        modifier = Modifier.size(imageSize),
        painter = painterResource(
            id = when (status) {
                Status.Good -> R.drawable.good_statues
                Status.Okay -> R.drawable.okay_statues
                Status.Poor -> R.drawable.poor_statues
                Status.Bad -> R.drawable.bad_staues
            }
        ),
        contentDescription = null,
    )
}

@Preview
@Composable
private fun Preview() {
    TudeeTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatueImage(24.dp, Status.Good)
            StatueImage(24.dp, Status.Okay)
            StatueImage(24.dp, Status.Poor)
            StatueImage(24.dp,Status.Bad)
        }
    }
}