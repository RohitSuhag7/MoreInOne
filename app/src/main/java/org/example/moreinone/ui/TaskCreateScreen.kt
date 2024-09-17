package org.example.moreinone.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import org.example.moreinone.R
import org.example.moreinone.common.SimpleText
import org.example.moreinone.common.SimpleTextField
import org.example.moreinone.common.toastMessage
import org.example.moreinone.model.entities.Todo
import org.example.moreinone.navigation.Screens
import org.example.moreinone.utils.DisablePastDates
import org.example.moreinone.utils.convertLongToTime
import org.example.moreinone.utils.reminder.scheduleAlarm
import org.example.moreinone.utils.timeFormatter
import org.example.moreinone.viewmodel.TodoViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCreateScreen(navController: NavController, todoJsonString: String?) {

    val todo = Gson().fromJson(todoJsonString, Todo::class.java)

    var taskName by remember { mutableStateOf(todo?.taskName ?: "") }
    var taskDesc by remember { mutableStateOf(todo?.taskDesc ?: "") }
    var taskDate by remember { mutableStateOf(todo?.createdOn ?: "") }
    var taskTime by remember { mutableStateOf(todo?.createdTime ?: "") }
    var checkedState by remember { mutableStateOf(todo?.isImportant ?: false) }

    val openDateDialog = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(selectableDates = DisablePastDates)

    val openTimeDialog = remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()

    val todoViewModel: TodoViewModel = hiltViewModel()
    val mContext = LocalContext.current

    var isErrorTaskName by rememberSaveable { mutableStateOf(false) }
    var isErrorTaskDesc by rememberSaveable { mutableStateOf(false) }
    var isErrorTaskDate by rememberSaveable { mutableStateOf(false) }
    var isErrorTaskTime by rememberSaveable { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    fun validate() {
        if (taskName.isNotEmpty() && taskDesc.isNotEmpty() && taskDate.isNotEmpty() && taskTime.isNotEmpty()) {
            todoViewModel.insertTodo(
                Todo(
                    id = todo?.id ?: 0,
                    taskName = taskName,
                    taskDesc = taskDesc,
                    createdOn = taskDate,
                    createdTime = taskTime,
                    isImportant = checkedState
                )
            ) { insertedId ->

                // Schedule Alarm Notification
                val time = taskTime.split(":", " ")
                scheduleAlarm(
                    context = mContext,
                    id = insertedId.toInt(),
                    title = taskName,
                    hour = time[0].toInt(),
                    minute = time[1].toInt()
                )
            }

            // Navigate to TaskListScreen
            navController.navigate(Screens.TaskListScreen.route)
        } else {
            isErrorTaskName = taskName.isEmpty()
            isErrorTaskDesc = taskDesc.isEmpty()
            isErrorTaskDate = taskDate.isEmpty()
            isErrorTaskTime = taskTime.isEmpty()

            toastMessage(
                mContext,
                mContext.getString(R.string.please_enter_valid_data)
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val toolbarText =
                        if (todo?.id == null)
                            stringResource(R.string.create_task)
                        else
                            stringResource(R.string.update_task)
                    Text(text = toolbarText)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 30.dp)
                .verticalScroll(scrollState)
        ) {
            // Task Name
            SimpleText(text = stringResource(R.string.task_name))
            SimpleTextField(
                textValue = taskName,
                onValueChanges = { taskName = it },
                hintText = stringResource(R.string.enter_your_task_name_here),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 16.dp, start = 8.dp, end = 8.dp),
                isError = isErrorTaskName,
                supportingText = {
                    if (isErrorTaskName) {
                        SimpleText(
                            text = stringResource(R.string.this_field_is_required),
                            textStyle = MaterialTheme.typography.labelMedium,
                            textColor = Color.Red
                        )
                    }
                }
            )

            // Task Description
            SimpleText(text = stringResource(R.string.task_desc))
            SimpleTextField(
                textValue = taskDesc,
                onValueChanges = { taskDesc = it },
                hintText = stringResource(R.string.enter_your_task_desc_here),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 16.dp, start = 8.dp, end = 8.dp),
                isError = isErrorTaskDesc,
                supportingText = {
                    if (isErrorTaskDesc) {
                        SimpleText(
                            text = stringResource(R.string.this_field_is_required),
                            textStyle = MaterialTheme.typography.labelMedium,
                            textColor = Color.Red
                        )
                    }
                }
            )

            // Task Date
            SimpleText(text = stringResource(R.string.task_date))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                SimpleTextField(
                    textValue = taskDate,
                    onValueChanges = { taskDate = it },
                    hintText = stringResource(R.string.select_date_from_calendar),
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 16.dp, start = 8.dp, end = 8.dp),
                    isError = isErrorTaskDate,
                    supportingText = {
                        if (isErrorTaskDate) {
                            SimpleText(
                                text = stringResource(R.string.this_field_is_required),
                                textStyle = MaterialTheme.typography.labelMedium,
                                textColor = Color.Red
                            )
                        }
                    }
                )
                IconButton(
                    onClick = {
                        openDateDialog.value = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        tint = Color.Unspecified,
                    )
                }
                if (openDateDialog.value) {
                    DatePickerDialog(
                        onDismissRequest = {
                            openDateDialog.value = false
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                openDateDialog.value = false
                                var date = mContext.getString(R.string.no_selection)
                                if (datePickerState.selectedDateMillis != null) {
                                    date = convertLongToTime(datePickerState.selectedDateMillis!!)
                                }
                                taskDate = date
                            }) {
                                SimpleText(text = stringResource(R.string.okay))
                            }
                        }) {
                        DatePicker(state = datePickerState)
                    }
                }
            }

            // Time Picker
            SimpleText(text = stringResource(R.string.task_time))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                SimpleTextField(
                    textValue = taskTime,
                    onValueChanges = { taskTime = it },
                    hintText = stringResource(R.string.select_time_from_picker),
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 16.dp, start = 8.dp, end = 8.dp),
                    isError = isErrorTaskTime,
                    supportingText = {
                        if (isErrorTaskTime) {
                            SimpleText(
                                text = stringResource(R.string.this_field_is_required),
                                textStyle = MaterialTheme.typography.labelMedium,
                                textColor = Color.Red
                            )
                        }
                    }
                )
                IconButton(
                    onClick = {
                        openTimeDialog.value = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.alarmclock),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        tint = Color.Unspecified,
                    )
                }
                if (openTimeDialog.value) {
                    DatePickerDialog(
                        onDismissRequest = {
                            openTimeDialog.value = false
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                openTimeDialog.value = false
                                val calendar = Calendar.getInstance()
                                calendar.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                                calendar.set(Calendar.MINUTE, timePickerState.minute)

                                val amPm = if (timePickerState.hour < 12)
                                    mContext.getString(R.string.am)
                                else
                                    mContext.getString(R.string.pm)

                                taskTime = "${timeFormatter(calendar.time.time)} $amPm"
                            }) {
                                SimpleText(text = stringResource(R.string.okay))
                            }
                        }) {
                        TimePicker(
                            state = timePickerState,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                }
            }

            // Task Important
            Row(
                modifier = Modifier
                    .padding(top = 16.dp, start = 50.dp)
            ) {
                SimpleText(text = stringResource(R.string.important))
                Checkbox(
                    checked = checkedState,
                    onCheckedChange = {
                        checkedState = it
                    },
                    modifier = Modifier
                        .padding(start = 50.dp)
                        .size(size = 20.dp)
                )
            }

            // Create Task Button
            ElevatedButton(
                onClick = {
                    validate()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
            ) {
                val buttonText =
                    if (todo?.id == null)
                        stringResource(R.string.create)
                    else
                        stringResource(R.string.update)
                SimpleText(
                    text = buttonText,
                    textStyle = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}
