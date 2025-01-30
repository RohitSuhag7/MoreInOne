package org.example.moreinone.ui.clock.alarm

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import org.example.moreinone.R
import org.example.moreinone.common.dialog.CustomDialogWithTextField
import org.example.moreinone.common.utils.EmptyScreen
import org.example.moreinone.common.utils.MyFloatingActionButton
import org.example.moreinone.common.utils.SimpleText
import org.example.moreinone.model.entities.Alarm
import org.example.moreinone.utils.timeFormatter
import org.example.moreinone.viewmodel.AlarmViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmScreen() {

    val alarmViewModel: AlarmViewModel = hiltViewModel()
    val getAllAlarm: List<Alarm> by alarmViewModel.getAllAlarm.collectAsState(initial = emptyList())

    val context = LocalContext.current

    val openLabelDialog = remember { mutableStateOf(false) }

    val openTimeDialog = remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()

    val alarmData = remember { mutableStateOf<Alarm?>(null) }
    val alarmId = remember { mutableIntStateOf(alarmData.value?.id ?: 0) }
    val alarmLabel = remember {
        mutableStateOf(
            alarmData.value?.alarmLabel ?: context.getString(R.string.add_label)
        )
    }
    val alarmFieldLabel = remember { mutableStateOf(alarmLabel.value) }

    val alarmTime = remember { mutableStateOf(alarmData.value?.alarmTime ?: "") }
    val alarmAmPM = remember { mutableStateOf(alarmData.value?.alarmAmPm ?: "") }
    val activateSetAlarm = remember { mutableStateOf(alarmData.value?.isAlarmSet ?: false) }
    val alarmDayList = remember { mutableStateListOf<String>() }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                SimpleText(
                    text = stringResource(id = R.string.alarm),
                    textStyle = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            })
        },
        floatingActionButton = {
            MyFloatingActionButton(
                onClick = {
                    openTimeDialog.value = true
                    alarmId.intValue = 0
                },
                imageVector = Icons.Filled.Add,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                containerColor = Color.Cyan,
                tint = Color.Black
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        if (getAllAlarm.isEmpty()) {
            EmptyScreen(
                text = "No Alarm Scheduled",
                paddingValues = paddingValues
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(paddingValues)
            ) {
                LazyColumn {
                    items(getAllAlarm.size) { index ->
                        AlarmCardView(
                            alarmLabel = getAllAlarm[index].alarmLabel.toString(),
                            onLabelClick = {
                                openLabelDialog.value = true
                                alarmId.intValue = getAllAlarm[index].id
                            },
                            alarmTime = getAllAlarm[index].alarmTime.toString(),
                            amPM = getAllAlarm[index].alarmAmPm.toString(),
                            onSetAlarmClick = {
                                openTimeDialog.value = true
                                alarmId.intValue = getAllAlarm[index].id
                            },
                            switchValue = getAllAlarm[index].isAlarmSet ?: false,
                            onSwitchValueChange = { bool ->
                                activateSetAlarm.value = bool
                                val updateAlarm =
                                    getAllAlarm[index].copy(isAlarmSet = activateSetAlarm.value)
                                alarmViewModel.addAlarm(updateAlarm)
                            },
                            onDeleteClick = {
                                alarmViewModel.deleteAlarm(getAllAlarm[index])
                            },
                            alarmDayList = getAllAlarm[index].alarmDays ?: mutableListOf()
                        )
                    }
                }
            }
        }

        // Open Label Dialog
        if (openLabelDialog.value) {
            CustomDialogWithTextField(
                onDismissRequest = {
                    openLabelDialog.value = false
                },
                onConfirmClick = {
                    if (alarmFieldLabel.value.isNotEmpty()) {
                        alarmLabel.value = alarmFieldLabel.value
                    } else {
                        alarmLabel.value = context.getString(R.string.add_label)
                    }

                    // Save Alarm Label in database
                    val updateAlarm = getAllAlarm.find { it.id == alarmId.intValue }
                    updateAlarm?.let {
                        alarmViewModel.addAlarm(it.copy(alarmLabel = alarmLabel.value))
                    }

                    openLabelDialog.value = false
                },
                textValue = alarmFieldLabel.value,
                onTextValueChange = { newValue ->
                    alarmFieldLabel.value = newValue
                },
                labelValue = stringResource(id = R.string.label)
            )
        }

        // Open Alarm Scheduler Dialog
        if (openTimeDialog.value) {
            SetAlarm(
                onDismiss = {
                    openTimeDialog.value = false
                },
                onConfirmClick = {
                    openTimeDialog.value = false
                    activateSetAlarm.value = true

                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                    calendar.set(Calendar.MINUTE, timePickerState.minute)

                    alarmTime.value = timeFormatter(calendar.time.time)
                    alarmAmPM.value = if (timePickerState.hour < 12) "am" else "pm"

                    // Save in database
                    alarmViewModel.addAlarm(
                        Alarm(
                            id = alarmId.intValue,
                            alarmLabel = alarmLabel.value,
                            alarmTime = alarmTime.value,
                            alarmAmPm = alarmAmPM.value,
                            isAlarmSet = activateSetAlarm.value,
                            alarmDays = alarmDayList
                        )
                    )
                },
                timePickerState = timePickerState
            )
        }
    }
}
