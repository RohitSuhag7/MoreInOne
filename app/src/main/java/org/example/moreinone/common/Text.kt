package org.example.moreinone.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

@Composable
fun SimpleText(
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge
) {
    Text(
        text = text,
        style = textStyle
    )
}
