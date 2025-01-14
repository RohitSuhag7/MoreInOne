package org.example.moreinone.ui.clock.alarm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import org.example.moreinone.R
import org.example.moreinone.common.dialog.CustomDialogWithTextField
import org.example.moreinone.common.utils.MyFloatingActionButton
import org.example.moreinone.common.utils.SimpleText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmScreen() {

    val context = LocalContext.current

    val alarmLabel = remember { mutableStateOf(context.getString(R.string.add_label)) }
    val alarmFieldLabel = remember { mutableStateOf(alarmLabel.value) }

    val openLabelDialog = remember { mutableStateOf(false) }

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
                onClick = { /*TODO*/ },
                imageVector = Icons.Filled.Add,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                containerColor = Color.Cyan,
                tint = Color.Black
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(it)
        ) {
            AlarmCardView(
                alarmLabel = alarmLabel.value,
                onLabelClick = {
                    openLabelDialog.value = true
                },
                alarmTime = "6:00",
                amPM = "am",
                switchValue = false,
                onSwitchValueChange = {})
        }

        // Open Dialog
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
                    openLabelDialog.value = false
                },
                textValue = alarmFieldLabel.value,
                onTextValueChange = { newValue ->
                    alarmFieldLabel.value = newValue
                },
                labelValue = stringResource(id = R.string.label)
            )
        }
    }
}
