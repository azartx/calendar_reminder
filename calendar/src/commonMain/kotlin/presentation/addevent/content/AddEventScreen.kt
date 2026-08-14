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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.solo4.calendarreminder.calendar.presentation.addevent.content.state.AddEventErrorState
import com.solo4.calendarreminder.calendar.presentation.addevent.content.state.AddEventScreenState
import com.solo4.calendarreminder.shared.calendar.generated.resources.Res
import com.solo4.calendarreminder.shared.calendar.generated.resources.ic_clock
import com.solo4.core.calendar.model.Millis
import com.solo4.core.uicomponents.MarkdownEditor
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
    onTimeEnabledChanged: (Boolean) -> Unit,
    onNotificationEnabledChanged: (Boolean) -> Unit,
    onSubmitButtonClicked: () -> Unit,
    onBackPressed: () -> Unit,
) {
    var isMarkdownPreviewEnabled by remember { mutableStateOf(false) }
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

        MarkdownEditor(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            value = screenState.description,
            isPreviewEnabled = isMarkdownPreviewEnabled,
            onPreviewEnabledChange = { isMarkdownPreviewEnabled = it },
            onValueChange = onDescriptionTextFieldChanged,
            isError = errorState.isDescriptionValid,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.weight(1f),
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
            Switch(
                modifier = Modifier.padding(start = 8.dp),
                checked = screenState.isTimeEnabled,
                onCheckedChange = onTimeEnabledChanged
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Send notification"
            )
            Switch(
                checked = screenState.isNotificationEnabled,
                onCheckedChange = onNotificationEnabledChanged
            )
        }

        if (screenState.isNotificationEnabled) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
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
