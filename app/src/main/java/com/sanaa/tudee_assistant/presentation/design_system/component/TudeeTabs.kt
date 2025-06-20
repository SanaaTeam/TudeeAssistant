package com.sanaa.tudee_assistant.presentation.design_system.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme

@Composable
fun TudeeScrollableTabs(
    tabs: List<TabItem>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current

    val textWidths = remember(tabs) {
        tabs.map { tab ->
            with(density) {
                textMeasurer.measure(
                    text = tab.label,
                ).size.width.toDp() + 32.dp
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Theme.color.surfaceHigh,
            contentColor = Theme.color.title,
            indicator = { tabPositions ->
                if (selectedTabIndex < tabPositions.size && selectedTabIndex < textWidths.size) {
                    CustomTabIndicator(
                        tabPosition = tabPositions[selectedTabIndex],
                        textWidth = textWidths[selectedTabIndex]
                    )
                }
            },
            divider = {
                Box(
                    modifier = Modifier
                        .height(1.dp)
                        .background(color = Theme.color.stroke)
                )
            }

        ) {
            tabs.forEachIndexed { index, tabItem ->
                CustomTab(
                    tabItem = tabItem,
                    isSelected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) }
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize().padding(bottom = 12.dp)
        ) {
            tabs.getOrNull(selectedTabIndex)?.content?.invoke()
        }
    }
}

@Composable
private fun CustomTabIndicator(
    tabPosition: TabPosition,
    textWidth: Dp,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .wrapContentSize(Alignment.BottomStart)
            .offset(
                x = tabPosition.left + (tabPosition.width - textWidth) / 2
            )
            .width(textWidth)
            .height(Theme.dimension.extraSmall)
            .background(
                color = Theme.color.secondary,
                shape = RoundedCornerShape(
                    topStart = Theme.dimension.regular,
                    topEnd = Theme.dimension.regular
                )
            )
    )
}

@Composable
private fun CustomTab(
    tabItem: TabItem,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val animatedColor by animateColorAsState(
        targetValue = if (isSelected) Theme.color.title else Theme.color.hint,
        animationSpec = tween(100),
    )

    Tab(
        selected = isSelected,
        onClick = onClick,

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = Theme.dimension.extraSmall),
            modifier = Modifier
                .fillMaxHeight()
                .padding(Theme.dimension.regular)
                .animateContentSize(
                    animationSpec = tween(100, easing = EaseOut),

                    )
        ) {
            Text(
                text = tabItem.label,
                style = Theme.textStyle.body.small,
                color = animatedColor
            )
            AnimatedVisibility(
                visible = isSelected,
                enter = fadeIn(animationSpec = tween(100)),
                exit = fadeOut(animationSpec = tween(100))
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(
                            color = Theme.color.surface,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tabItem.count.toString(),
                        style = Theme.textStyle.label.small,
                        color = Theme.color.body,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TudeeScrollableTabsPreview() {
    val tabs = listOf(
        TabItem(
            label = stringResource(R.string.in_progress_task_status),
            count = 14
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.color.surface),
                contentAlignment = Alignment.Center
            ) {
                Text("In Progress Content", color = Theme.color.title)
            }
        },
        TabItem(
            label = stringResource(R.string.in_progress_task_status),
            count = 0
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.color.surface),
                contentAlignment = Alignment.Center
            ) {
                Text("To Do Content", color = Theme.color.title)
            }
        },

        TabItem(
            label = stringResource(R.string.todo_task_status),
            count = 0
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.color.surface),
                contentAlignment = Alignment.Center
            ) {
                Text("Done Content", color = Theme.color.title)
            }
        }
    )

    var selectedTab by remember { mutableIntStateOf(0) }

    TudeeTheme(false) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Theme.color.surface)
        ) {
            TudeeScrollableTabs(
                tabs = tabs,
                selectedTabIndex = selectedTab,
                onTabSelected = { selectedTab = it },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

data class TabItem(
    val label: String,
    val count: Int,
    val content: @Composable () -> Unit
)