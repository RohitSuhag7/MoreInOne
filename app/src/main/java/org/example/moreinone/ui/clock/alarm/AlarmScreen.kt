package org.example.moreinone.ui.clock.alarm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.example.moreinone.R
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
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Alarm Screen")
        }
    }
}
