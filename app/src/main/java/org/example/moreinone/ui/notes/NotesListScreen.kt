package org.example.moreinone.ui.notes

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.example.moreinone.R
import org.example.moreinone.common.utils.MyFloatingActionButton
import org.example.moreinone.common.utils.MySearchBar
import org.example.moreinone.ui.theme.PurpleGrey40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListScreen() {

    var searchQuery by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MySearchBar(
                query = searchQuery,
                onQueryChange = {
                    searchQuery = it
                },
                onSearch = {},
                active = active,
                onActiveChange = {
                    active = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                colors = SearchBarDefaults.colors(PurpleGrey40),
                placeholderText = stringResource(id = R.string.search_your_notes),
                leadingIconImageVector = Icons.Rounded.Search
            )
        }, floatingActionButton = {
            MyFloatingActionButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(16.dp)
                    .animateContentSize(tween(300)),
                imageVector = Icons.Default.Add
            )
        }, containerColor = Color.Black
    ) {
        Column(modifier = Modifier.padding(it)) {
            Text(text = "Notes Application")
        }

    }
}
