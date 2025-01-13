package org.example.moreinone.ui.clock.stopwatch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.example.moreinone.R
import org.example.moreinone.common.utils.SimpleText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StopwatchScreen() {

    val isStopwatchRunning = remember { mutableStateOf(false) }

    val elapsedTime = remember { mutableLongStateOf(0L) }

    val lapList = remember { mutableStateListOf<Pair<Int, String>>() }

    // Increase milliseconds when the stopwatch is running
    LaunchedEffect(isStopwatchRunning.value) {
        if (isStopwatchRunning.value) {
            while (isStopwatchRunning.value) {
                delay(10) // Delay for 10 millisecond
                elapsedTime.longValue += 1 // Increment milliseconds by 1
            }
        }
    }

    // Convert milliseconds to hours, minutes, seconds, and milliseconds
    val hours = (elapsedTime.longValue / 100) / 3600
    val minutes = (elapsedTime.longValue / 100 % 3600) / 60
    val seconds = (elapsedTime.longValue / 100) % 60
    val milliseconds = elapsedTime.longValue % 100

    Scaffold(
        topBar = {
            TopAppBar(title = {
                SimpleText(
                    text = stringResource(id = R.string.stopwatch),
                    textStyle = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            })
        },
        floatingActionButton = {
            StopwatchControls(
                isStopwatchRunning = isStopwatchRunning.value,
                elapsedTime = elapsedTime.longValue,
                onResetClick = {
                    isStopwatchRunning.value = false
                    lapList.clear() // Reset lap list
                    elapsedTime.longValue = 0L // Reset elapsedTime
                },
                onPlayPauseClick = {
                    isStopwatchRunning.value = !isStopwatchRunning.value
                },
                onLapClick = {
                    val lapTime = "%02d:%02d:%02d.%02d".format(hours, minutes, seconds, milliseconds)
                    lapList.add((Pair(lapList.size + 1, lapTime)))
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.3f))

            StopwatchDisplay(
                hour = stringResource(id = R.string.hour, hours),
                minutes = stringResource(id = R.string.minutes, minutes),
                seconds = stringResource(
                    id = R.string.seconds,
                    "%02d".format(seconds)
                ),
                milliseconds = stringResource(
                    id = R.string.milliseconds,
                    "%02d".format(milliseconds)
                ),
                lapList = lapList,
                hourVisible = hours != 0L,
                minutesVisible = minutes != 0L
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
