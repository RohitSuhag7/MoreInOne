package org.example.moreinone.common

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

@Composable
fun SimpleTextField(
    textValue: String,
    onValueChanges: (String) -> Unit,
    hintText: String = "",
) {
    TextField(
        value = textValue,
        onValueChange = {
            onValueChanges(it)
        },
        placeholder = {
            Text(text = hintText)
        }
    )
}
