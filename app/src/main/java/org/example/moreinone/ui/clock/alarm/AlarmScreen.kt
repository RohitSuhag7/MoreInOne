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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.moreinone.R
import org.example.moreinone.common.utils.MyFloatingActionButton
import org.example.moreinone.common.utils.SimpleText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmScreen() {

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
                alarmTime = "6:00",
                amPM = "am",
                switchValue = false,
                onSwitchValueChange = {})
        }
    }
}
