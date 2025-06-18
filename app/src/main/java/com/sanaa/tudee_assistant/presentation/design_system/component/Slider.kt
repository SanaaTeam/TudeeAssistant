package com.sanaa.tudee_assistant.presentation.design_system.component

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.TudeeStatus
import com.sanaa.tudee_assistant.presentation.state.SliderState

@Composable
fun Slider(
    sliderState: SliderState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Theme.dimension.regular)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Theme.dimension.small),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Theme.dimension.small)
            ) {
                Text(
                    text = sliderState.title,
                    style = Theme.textStyle.title.small,
                    color = Theme.color.title
                )

                StatusImage(20.dp, sliderState.status)
            }

            Text(
                text = sliderState.description,
                style = Theme.textStyle.body.small,
                color = Theme.color.body
            )
        }

        Box {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Theme.color.primary.copy(alpha = 0.16f))
                    .align(Alignment.BottomStart)
                    .padding(bottom = Theme.dimension.extraSmall)
                    .size(76.dp),
            )

            Image(
                modifier = Modifier
                    .padding(start = 3.dp)
                    .width(61.dp)
                    .height(92.dp),
                painter = painterResource(
                    id = when (sliderState.status) {
                        TudeeStatus.OKAY -> R.drawable.robot1
                        TudeeStatus.GOOD -> R.drawable.robot2
                        TudeeStatus.BAD -> R.drawable.robot3
                        TudeeStatus.POOR -> R.drawable.robot1
                    }
                ),
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TudeeTheme(isDarkTheme = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surface)
                .padding(Theme.dimension.medium),
            verticalArrangement = Arrangement.spacedBy(21.dp)
        ) {
            Slider(
                SliderState(
                    title = "Stay working!",
                    description = "You've completed 3 out of 10 tasks Keep going!",
                    TudeeStatus.OKAY,
                )
            )

            Slider(
                SliderState(
                    title = "Tadaa!",
                    description = "You’re doing amazing!!!\n" +
                            "Tudee is proud of you.",
                    TudeeStatus.GOOD,
                )
            )

            Slider(
                SliderState(
                    title = "Zero progress?!",
                    description = "You just scrolling, not working. Tudee is watching. back to work!!!",
                    TudeeStatus.BAD,
                )
            )
        }
    }
}