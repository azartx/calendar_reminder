package com.solo4.calendarreminder.presentation.screens.addevent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.solo4.calendarreminder.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(navController: NavHostController) {

    val viewModel = viewModel<AddEventViewModel>()
    val screenState by viewModel.screenState.collectAsState()
    val errorState by viewModel.errorDelegate.errorState.collectAsState()
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
                DatePicker(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    state = datePickerState
                )
            }
        }

        if (screenState.isTimePickerVisible) {
            DatePickerDialog(
                onDismissRequest = viewModel::onTimePickerDismissed,
                confirmButton = {},
            ) {
                TimePicker(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp),
                    state = timePickerState
                )

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Send notification before minutes"
                )

                Row(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    viewModel.scheduleBeforeMillis.forEach { millis ->
                        FilterChip(
                            selected = screenState.selectedScheduleBeforeMillis == millis,
                            onClick = { viewModel.onSchedulingFilterChipClicked(millis) },
                            label = { Text(text = millis.toMinutes().toString()) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = screenState.title,
            placeholder = { Text(text = "Title") },
            isError = errorState.isTitleValid,
            onValueChange = viewModel::onTitleTextFieldChanged
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            value = screenState.description,
            placeholder = { Text(text = "Description") },
            isError = errorState.isDescriptionValid,
            onValueChange = viewModel::onDescriptionTextFieldChanged
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = screenState.selectedDate,
            readOnly = true,
            onValueChange = {},
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable(
                        onClick = viewModel::onDatePickerButtonPressed
                    ),
                    painter = painterResource(id = R.drawable.ic_clock),
                    contentDescription = null
                )
            }
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            shape = RoundedCornerShape(8.dp),
            onClick = viewModel::onSubmitButtonClicked
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Submit"
            )
        }
    }
}