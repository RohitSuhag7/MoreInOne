package org.example.moreinone.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import org.example.moreinone.model.Todo
import org.example.moreinone.navigation.Screens
import org.example.moreinone.utils.DisablePastDates
import org.example.moreinone.utils.convertLongToTime
import org.example.moreinone.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCreateScreen(navController: NavController, todoJsonString: String?) { //name: String?, desc: String?, getDate: String?
    var taskName by remember { mutableStateOf("") }
    var taskDesc by remember { mutableStateOf("") }
    var taskDate by remember { mutableStateOf("") }
    val openDialog = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(selectableDates = DisablePastDates)
    var checkedState by remember { mutableStateOf(false) }

    val todoViewModel: TodoViewModel = hiltViewModel()
    val mContext = LocalContext.current

    fun validate() {
        if (taskName.isNotEmpty() || taskDesc.isNotEmpty() || taskDate.isNotEmpty()) {
            todoViewModel.insertTodo(
                Todo(
                    taskName = taskName,
                    taskDesc = taskDesc,
                    createdOn = taskDate,
                    isImportant = checkedState
                )
            )
            navController.navigate(Screens.TaskListScreen.route)
        } else {
            toastMessage(
                mContext,
                mContext.getString(R.string.please_enter_valid_data)
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.create_task)) },
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

        val todo = Gson().fromJson(todoJsonString, Todo::class.java)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 50.dp)
        ) {
            // Task Name
            SimpleText(text = stringResource(R.string.task_name))
            SimpleTextField(
                textValue = todo?.taskName ?: taskName,
                onValueChanges = { taskName = it },
                stringResource(R.string.enter_your_task_name_here),
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 24.dp, start = 8.dp, end = 8.dp)
            )

            // Task Description
            SimpleText(text = stringResource(R.string.task_desc))
            SimpleTextField(
                textValue = todo?.taskDesc ?: taskDesc,
                onValueChanges = { taskDesc = it },
                stringResource(R.string.enter_your_task_desc_here),
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 24.dp, start = 8.dp, end = 8.dp)
            )

            // Task Date
            SimpleText(text = stringResource(R.string.task_date))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                SimpleTextField(
                    textValue = todo?.createdOn ?: taskDate,
                    onValueChanges = { taskDesc = taskDate },
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
                        contentDescription = null,
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
                    .padding(top = 100.dp)
            ) {
                SimpleText(
                    text = stringResource(R.string.create),
                    textStyle = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}
