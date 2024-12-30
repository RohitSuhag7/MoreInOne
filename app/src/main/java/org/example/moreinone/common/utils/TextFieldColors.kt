package org.example.moreinone.common.utils

import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun textFieldColors(
    textFieldColor: Color
): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedContainerColor = textFieldColor,
        unfocusedContainerColor = textFieldColor,
        disabledContainerColor = textFieldColor,
        cursorColor = Color.White,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )
}
