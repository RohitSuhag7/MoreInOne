package org.example.moreinone.ui.clock.stopwatch

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.moreinone.R
import org.example.moreinone.common.utils.SimpleText
import org.example.moreinone.ui.theme.LightViolet
import org.example.moreinone.ui.theme.Purple

@Composable
fun StopwatchDisplay(
    hour: String,
    minutes: String,
    seconds: String,
    milliseconds: String,
    lapList: List<Pair<Int, String>>,
    hourVisible: Boolean = false,
    minutesVisible: Boolean = false,
) {

    Box(
        modifier = Modifier
            .size(300.dp)
            .border(
                width = 8.dp,
                color = Color.Gray,
                shape = CircleShape
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row {
                AnimatedVisibility(visible = hourVisible) {
                    SimpleText(
                        text = hour,
                        textStyle = TextStyle(fontSize = 40.sp)
                    )
                }
                AnimatedVisibility(visible = minutesVisible) {
                    SimpleText(
                        text = minutes,
                        textStyle = TextStyle(fontSize = 40.sp)
                    )
                }
                SimpleText(
                    text = seconds,
                    textStyle = TextStyle(fontSize = 40.sp)
                )
            }
            SimpleText(
                text = milliseconds,
                textStyle = TextStyle(fontSize = 30.sp)
            )
        }
    }

    Spacer(modifier = Modifier.padding(8.dp))

    StopwatchLapDisplay(lapList = lapList)
}

@Composable
fun StopwatchLapDisplay(lapList: List<Pair<Int, String>>) {
    val listState = rememberLazyListState()

    if (lapList.isNotEmpty()) {
        // Ensure latest item is visible when lapList is updated
        LaunchedEffect(lapList.size) {
            listState.animateScrollToItem(lapList.size - 1)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            LazyColumn(
                state = listState,
                reverseLayout = true
            ) {
                items(lapList.toList()) { (lap, time) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SimpleText(
                            text = "# $lap",
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                        SimpleText(text = time)
                    }
                }
            }
        }
    }
}

@Composable
fun StopwatchControls(
    isStopwatchRunning: Boolean,
    elapsedTime: Long,
    onPlayPauseClick: () -> Unit,
    onResetClick: () -> Unit,
    onLapClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        IconButton(
            onClick = onResetClick,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .alpha(if (elapsedTime == 0L) 0f else 1f)
                .background(color = Purple)
        ) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = "reset"
            )
        }
        IconButton(
            onClick = onPlayPauseClick,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(color = LightViolet)
        ) {
            Icon(
                painterResource(id = if (isStopwatchRunning) R.drawable.ic_pause else R.drawable.ic_play_arrow),
                contentDescription = "play and pause",
                tint = Color.Black
            )
        }
        IconButton(
            onClick = onLapClick,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .alpha(if (isStopwatchRunning) 1f else 0f)
                .background(color = Purple)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_stopwatch),
                contentDescription = "lap"
            )
        }
    }
}
