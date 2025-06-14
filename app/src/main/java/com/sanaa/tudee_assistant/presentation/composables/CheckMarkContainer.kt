package com.sanaa.tudee_assistant.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R

@Composable
fun CheckMarkContainer() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 15.dp, top = 2.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Image(
            painter = painterResource(R.drawable.checkmark_container),
            contentDescription = null,
        )
    }
}