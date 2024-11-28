package org.example.moreinone.common.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    colors: SearchBarColors,
    placeholderText: String,
    placeholderTextTint: Color = Color.White,
    leadingIconImageVector: ImageVector,
    leadingIconTint: Color = Color.White
) {
    SearchBar(
        query = query,
        onQueryChange = {
            onQueryChange(it)
        },
        onSearch = {
            onSearch(it)
        },
        active = active,
        onActiveChange = {
            onActiveChange(it)
        },
        modifier = modifier,
        colors = colors,
        placeholder = {
            Text(
                text = placeholderText,
                color = placeholderTextTint
            )
        },
        leadingIcon = {
            Icon(
                imageVector = leadingIconImageVector,
                contentDescription = "",
                tint = leadingIconTint
            )
        }
    ) {

    }
}
