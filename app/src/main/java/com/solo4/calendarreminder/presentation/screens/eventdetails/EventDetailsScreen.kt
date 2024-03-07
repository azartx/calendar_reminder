package com.solo4.calendarreminder.presentation.screens.eventdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun EventDetailsScreen() {

    val viewModel = viewModel<EventDetailsViewModel>()
    val event = remember { viewModel.event }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = event.title
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = event.description
        )
    }
}