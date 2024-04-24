package org.example.moreinone.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.moreinone.common.SimpleText
import org.example.moreinone.common.SimpleTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCreateScreen() {
    var text by remember { mutableStateOf("") }
    Scaffold (
        topBar = {
            TopAppBar(title = { Text(text = "Create Task") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ))
        }
    ) {
        Column(modifier = Modifier
            .padding(it)
            .padding(horizontal = 16.dp, vertical = 16.dp)) {
            SimpleText(text = "Task Name")
            SimpleTextField(textValue = text, onValueChanges = { text = it}, "e.g. GYM")
        }
    }
}
