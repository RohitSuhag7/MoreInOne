package org.example.moreinone.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.example.moreinone.R
import org.example.moreinone.common.SimpleText
import org.example.moreinone.common.SimpleTextField
import org.example.moreinone.utils.convertLongToTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCreateScreen() {
    var taskName by remember { mutableStateOf("") }
    var taskDesc by remember { mutableStateOf("") }
    var taskDate by remember { mutableStateOf("") }
    val openDialog = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Create Task") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            // Task Name
            SimpleText(text = stringResource(R.string.task_name))
            SimpleTextField(
                textValue = taskName, onValueChanges = { taskName = it },
                stringResource(R.string.enter_your_task_name_here),
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 24.dp, start = 8.dp, end = 8.dp)
            )

            // Task Description
            SimpleText(text = stringResource(R.string.task_desc))
            SimpleTextField(
                textValue = taskDesc, onValueChanges = { taskDesc = it },
                stringResource(R.string.enter_your_task_desc_here),
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 24.dp, start = 8.dp, end = 8.dp)
            )

            // Task Date
            SimpleText(text = stringResource(R.string.task_date))
            Row(modifier = Modifier.fillMaxWidth()) {
                SimpleTextField(
                    textValue = taskDate, onValueChanges = { taskDesc = taskDate },
                    stringResource(R.string.select_date_from_calendar),
                    Modifier
                        .padding(top = 8.dp, bottom = 24.dp, start = 8.dp, end = 8.dp)
                )
                IconButton(
                    onClick = {
                        openDialog.value = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "DatePicker",
                        modifier = Modifier.fillMaxSize(),
                        tint = Color.Unspecified,
                    )
                }
                if (openDialog.value) {
                    DatePickerDialog(
                        onDismissRequest = {
                            openDialog.value = false
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                openDialog.value = false
                                var date = "No Selection"
                                if (datePickerState.selectedDateMillis != null) {
                                    date = convertLongToTime(datePickerState.selectedDateMillis!!)
                                }
                                taskDate = date
                            }) {
                                SimpleText(text = "Okay")
                            }
                        }) {
                        DatePicker(state = datePickerState)
                    }
                }
            }
        }
    }
}
