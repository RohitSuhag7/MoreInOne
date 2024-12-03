package org.example.moreinone.common.utils

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit

@Composable
fun SimpleTextField(
    textValue: String,
    onValueChanges: (String) -> Unit,
    textStyle: TextStyle = LocalTextStyle.current,
    hintText: String = "",
    placeHolderFontSize: TextUnit = TextUnit.Unspecified,
    modifier: Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null,
    textFieldColors: TextFieldColors = TextFieldDefaults.colors(),
) {
    TextField(
        value = textValue,
        onValueChange = {
            onValueChanges(it)
        },
        placeholder = {
            Text(
                text = hintText,
                fontSize = placeHolderFontSize
            )
        },
        modifier = modifier,
        textStyle = textStyle,
        enabled = enabled,
        isError = isError,
        supportingText = {
            if (supportingText != null) {
                supportingText()
            }
        },
        colors = textFieldColors
    )
}
