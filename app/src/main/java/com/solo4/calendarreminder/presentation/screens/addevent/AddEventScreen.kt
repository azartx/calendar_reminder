package com.solo4.calendarreminder.presentation.screens.addevent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(navController: NavHostController) {

    val viewModel = viewModel<AddEventViewModel>()
    val screenState by viewModel.screenState.collectAsState()
    val datePickerState by viewModel.datePickerState.collectAsState()
    val timePickerState by viewModel.timePickerState.collectAsState()

    LifecycleResumeEffect(key1 = "") {
        val job = lifecycleScope.launch {
            viewModel.navigationState.collectLatest { navController.popBackStack() }
        }
        onPauseOrDispose { job.cancel() }
    }

    Column {

        if (screenState.isDatePickerVisible) {
            DatePickerDialog(
                onDismissRequest = viewModel::onDismissDatePickerClicked,
                confirmButton = {},
            ) {
                DatePicker(state = datePickerState)
            }
        }

        if (screenState.isTimePickerVisible) {
            DatePickerDialog(
                onDismissRequest = viewModel::onTimePickerDismissed,
                confirmButton = {},
            ) {
                TimePicker(state = timePickerState)
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = screenState.selectedDate)
            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = viewModel::onDatePickerButtonPressed) { Text(text = "Select date") }
        }

        TextField(value = screenState.title, onValueChange = viewModel::onTitleTextFieldChanged)
        TextField(value = screenState.description, onValueChange = viewModel::onDescriptionTextFieldChanged)

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            onClick = viewModel::onSubmitButtonClicked
        ) {
            Text(text = "Submit")
        }
    }
}