package org.example.moreinone.ui.clock.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.moreinone.R
import org.example.moreinone.common.utils.SimpleText
import org.example.moreinone.common.utils.dynamicAnnotatedString
import org.example.moreinone.common.utils.inlineContent

@Composable
fun AlarmCardView(
    alarmLabel: String,
    onLabelClick: () -> Unit,
    alarmTime: String,
    amPM: String,
    onSetAlarmClick: () -> Unit,
    switchValue: Boolean,
    onSwitchValueChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit,
    alarmDayList: MutableList<String>
) {

    val isCardExpended = remember { mutableStateOf(false) }

    // Function to get the display text for selected days
    val dayText = if (alarmDayList.isEmpty()) {
        stringResource(id = R.string.not_scheduled)
    } else if (alarmDayList.size == 7) {
        stringResource(id = R.string.every_day)
    } else {
        alarmDayList.joinToString(", ")
    }

    Card(
        onClick = {
            isCardExpended.value = !isCardExpended.value
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = dynamicAnnotatedString(alarmLabel),
                    style = TextStyle(color = if (switchValue) Color.White else Color.Unspecified),
                    inlineContent = inlineContent(painterIcon = painterResource(id = R.drawable.ic_label)),
                    modifier = Modifier.clickable {
                        onLabelClick()
                    })
                if (isCardExpended.value) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_up),
                        contentDescription = "drop up",
                        modifier = Modifier
                            .background(
                                color = Color.Gray, shape = CircleShape
                            )
                            .clickable {
                                isCardExpended.value = false
                            },
                        tint = Color.White
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_down),
                        contentDescription = "drop down",
                        modifier = Modifier
                            .background(
                                color = Color.Gray, shape = CircleShape
                            )
                            .clickable {
                                isCardExpended.value = true
                            },
                        tint = Color.White
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                SimpleText(text = alarmTime, textStyle = TextStyle(
                    fontSize = 40.sp,
                    color = if (switchValue) Color.White else Color.Unspecified
                ), modifier = Modifier.clickable {
                    onSetAlarmClick()
                })
                SimpleText(text = amPM,
                    textStyle = TextStyle(color = if (switchValue) Color.White else Color.Unspecified),
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .clickable {
                            onSetAlarmClick()
                        })
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SimpleText(
                    text = dayText,
                    textStyle = TextStyle(color = if (switchValue) Color.White else Color.Unspecified)
                )
                Switch(checked = switchValue, onCheckedChange = {
                    onSwitchValueChange(it)
                })
            }

            if (isCardExpended.value) {
                CardExpended(
                    alarmDay = alarmDayList,
                    onDeleteClick = onDeleteClick
                )
            }
        }
    }
}

@Composable
fun CardExpended(
    alarmDay: MutableList<String>,
    onDeleteClick: () -> Unit
) {
    val weekdaysOrder = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    val weeksList = listOf(
        "Sun" to "S",
        "Mon" to "M",
        "Tue" to "T",
        "Wed" to "W",
        "Thu" to "T",
        "Fri" to "F",
        "Sat" to "S"
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(weeksList.size) { day ->
            val (fullDayName, shortDayName) = weeksList[day]
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .border(
                        width = 2.dp, color = Color.Gray, shape = CircleShape
                    )
                    .background(
                        color = if (alarmDay.contains(fullDayName)) Color.White else Color.Unspecified,
                        shape = CircleShape
                    ), contentAlignment = Alignment.Center
            ) {
                SimpleText(
                    text = shortDayName,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(8.dp)
                        .clickable {
                            if (alarmDay.contains(fullDayName)) {
                                alarmDay.remove(fullDayName)
                            } else {
                                alarmDay.add(fullDayName)
                            }

                            // Sort the days based on weekdaysOrder to always display in series
                            alarmDay.sortBy { weekdaysOrder.indexOf(it) }
                        },
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = if (alarmDay.contains(fullDayName)) Color.Black else Color.Unspecified
                    )
                )
            }
        }
    }

    Text(text = dynamicAnnotatedString(label = stringResource(id = R.string.delete)),
        inlineContent = inlineContent(painterIcon = painterResource(id = R.drawable.ic_delete)),
        color = Color.White,
        modifier = Modifier
            .padding(top = 8.dp)
            .clickable {
                onDeleteClick()
            })
}
