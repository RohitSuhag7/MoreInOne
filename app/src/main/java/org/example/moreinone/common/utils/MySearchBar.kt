package org.example.moreinone.common.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction

@Composable
fun MySearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
    placeholderText: String,
    placeholderTextTint: Color = Color.White,
    leadingIconImageVector: ImageVector,
    leadingIconTint: Color = Color.White,
    onClickLeadingIcon: () -> Unit,
    onClickTrailingIcon: () -> Unit,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    onKeyboardSearchClick: () -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = {
            onQueryChange(it)
        },
        modifier = modifier,
        textStyle = textStyle,
        placeholder = {
            SimpleText(
                text = placeholderText,
                textColor = placeholderTextTint
            )
        }, leadingIcon = {
            Icon(
                imageVector = leadingIconImageVector,
                contentDescription = "leading back arrow icon",
                tint = leadingIconTint,
                modifier = Modifier.clickable {
                    onClickLeadingIcon()
                }
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.clickable {
                    onClickTrailingIcon()
                }
            )
        },
        singleLine = true,
        colors = colors,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onKeyboardSearchClick()
            }
        )
    )
}
