package com.sanaa.tudee_assistant.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.composables.HorizontalSpace
import com.sanaa.tudee_assistant.presentation.composables.VerticalSpace
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.Status

@Composable
fun Slider(title: String, description: String, status: Status) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(
            modifier = Modifier
                .weight(1f),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    style = Theme.textStyle.title.small,
                    color = Theme.color.title
                )

                HorizontalSpace(8.dp)

                StatusImage(20.dp, status)
            }

            VerticalSpace(8.dp)

            Text(
                text = description,
                style = Theme.textStyle.body.small,
                color = Theme.color.body
            )
        }

        HorizontalSpace(12.dp)

        Box(contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier.size(76.dp),
                painter = painterResource(id = R.drawable.circle_back),
                contentDescription = null,
            )

            Image(
                modifier = Modifier
                    .width(61.dp)
                    .height(92.dp),
                painter = painterResource(id = R.drawable.robot1),
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TudeeTheme(isDarkTheme = false) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(21.dp)
        ) {
            Slider(
                title = "Stay working!",
                description = "You've completed 3 out of 10 tasks Keep going!",
                Status.Okay
            )

            Slider(
                title = "Tadaa!",
                description = "You’re doing amazing!!!\n" +
                        "Tudee is proud of you.",
                Status.Good
            )

            Slider(
                title = "Zero progress?!",
                description = "You just scrolling, not working. Tudee is watching. back to work!!!",
                Status.Bad
            )

            Slider(
                title = "Nothing on your list…",
                description = "Fill your day with something awesome.",
                Status.Poor
            )
        }
    }
}