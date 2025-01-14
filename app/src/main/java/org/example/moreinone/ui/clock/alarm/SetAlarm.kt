package org.example.moreinone.ui.clock.alarm

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.example.moreinone.R
import org.example.moreinone.common.utils.SimpleText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetAlarm(onDismiss: () -> Unit) {

    val timePicker = remember { mutableStateOf(true) }
    val timePickerState = rememberTimePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                if (timePicker.value) {
                    IconButton(onClick = {
                        timePicker.value = false
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_keyboard),
                            contentDescription = "time input"
                        )
                    }
                } else {
                    IconButton(onClick = {
                        timePicker.value = true
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_access_time),
                            contentDescription = "time picker"
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                TextButton(onClick = onDismiss) {
                    SimpleText(text = stringResource(id = R.string.cancel))
                }

                TextButton(onClick = { /*TODO*/ }) {
                    SimpleText(text = stringResource(id = R.string.ok))
                }
            }
        })
    {
        SimpleText(
            text = stringResource(id = R.string.select_time),
            modifier = Modifier.padding(20.dp)
        )
        if (timePicker.value) {
            TimePicker(
                state = timePickerState,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            TimeInput(
                state = timePickerState,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
