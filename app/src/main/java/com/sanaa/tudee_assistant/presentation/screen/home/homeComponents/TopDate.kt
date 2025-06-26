package com.sanaa.tudee_assistant.presentation.screen.home.homeComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.screen.home.HomeScreenUiState
import com.sanaa.tudee_assistant.presentation.utils.DateFormater.formatDateTime

@Composable
fun TopDate(state: HomeScreenUiState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(Theme.dimension.medium),
            painter = painterResource(id = R.drawable.calendar_favorite_01),
            contentDescription = null,
            tint = Theme.color.body
        )

        Text(
            modifier = Modifier.padding(start = Theme.dimension.small),
            text = "${stringResource(R.string.today)}, " + state.todayDate.formatDateTime(context = LocalContext.current),
            color = Theme.color.body,
            style = Theme.textStyle.label.medium
        )
    }
}