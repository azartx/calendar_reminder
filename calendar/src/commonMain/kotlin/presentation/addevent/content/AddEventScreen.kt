package com.solo4.calendarreminder.calendar.presentation.addevent.content

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
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.solo4.calendarreminder.calendar.presentation.addevent.content.state.AddEventErrorState
import com.solo4.calendarreminder.calendar.presentation.addevent.content.state.AddEventScreenState
import com.solo4.calendarreminder.shared.calendar.generated.resources.Res
import com.solo4.calendarreminder.shared.calendar.generated.resources.ic_clock
import com.solo4.core.calendar.model.Millis
import com.solo4.core.uicomponents.Toolbar
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    modifier: Modifier,
    screenState: AddEventScreenState,
    errorState: AddEventErrorState,
    datePickerState: DatePickerState,
    timePickerState: TimePickerState,
    scheduleBeforeMillis: List<Millis>,
    onDismissDatePickerClick: () -> Unit,
    onDismissTimePickerClick: () -> Unit,
    onSchedulingFilterChipClicked: (Millis) -> Unit,
    onTitleTextFieldChanged: (String) -> Unit,
    onDescriptionTextFieldChanged: (String) -> Unit,
    onDatePickerButtonPressed: () -> Unit,
    onSubmitButtonClicked: () -> Unit,
    onBackPressed: () -> Unit,
) {
    Column(modifier = modifier) {
        Toolbar(
            title = "Create new event",
            onBackPressed = onBackPressed
        )
        if (screenState.isDatePickerVisible) {
            DatePickerDialog(
                onDismissRequest = onDismissDatePickerClick,
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
                onDismissRequest = onDismissTimePickerClick,
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
                    scheduleBeforeMillis.forEach { millis ->
                        FilterChip(
                            selected = screenState.selectedScheduleBeforeMillis == millis,
                            onClick = { onSchedulingFilterChipClicked.invoke(millis) },
                            label = { Text(text = millis.toMinutes().toString()) }
                        )
                    }
                }
            }
        }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = screenState.title,
            placeholder = { Text(text = "Title") },
            isError = errorState.isTitleValid,
            onValueChange = onTitleTextFieldChanged
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            value = screenState.description,
            placeholder = { Text(text = "Description") },
            isError = errorState.isDescriptionValid,
            onValueChange = onDescriptionTextFieldChanged
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
                        onClick = onDatePickerButtonPressed
                    ),
                    painter = painterResource(resource = Res.drawable.ic_clock),
                    contentDescription = null
                )
            }
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            shape = RoundedCornerShape(8.dp),
            onClick = onSubmitButtonClicked
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Submit"
            )
        }
    }
}