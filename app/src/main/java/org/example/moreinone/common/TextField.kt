package org.example.moreinone.common

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SimpleTextField(
    textValue: String,
    onValueChanges: (String) -> Unit,
    hintText: String = "",
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    TextField(
        value = textValue,
        onValueChange = {
            onValueChanges(it)
        },
        placeholder = {
            Text(text = hintText)
        },
        modifier = modifier,
        enabled = enabled
    )
}
