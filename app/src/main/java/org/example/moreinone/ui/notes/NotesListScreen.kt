package org.example.moreinone.ui.notes

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import org.example.moreinone.R
import org.example.moreinone.common.utils.EmptyScreen
import org.example.moreinone.common.utils.MyFloatingActionButton
import org.example.moreinone.common.utils.MySearchBar
import org.example.moreinone.model.entities.Notes
import org.example.moreinone.navigation.Screens
import org.example.moreinone.ui.theme.PurpleGrey40
import org.example.moreinone.utils.Constants.NOTES_NAV_KEY
import org.example.moreinone.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListScreen(navController: NavController) {

    var searchQuery by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    val notesViewModel: NotesViewModel = hiltViewModel()

    val getAllNotes: List<Notes> by notesViewModel.getAllNotes.collectAsState(initial = emptyList())

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
                onClick = {
                    navController.navigate(Screens.CreateNotesScreen.route)
                },
                modifier = Modifier
                    .padding(16.dp)
                    .animateContentSize(tween(300)),
                imageVector = Icons.Default.Add
            )
        }, containerColor = Color.Black
    ) { paddingValues ->
        if (getAllNotes.isEmpty()) {
            EmptyScreen(
                text = stringResource(R.string.no_notes_available),
                paddingValues = paddingValues
            )
        } else {
            Column(modifier = Modifier.padding(paddingValues)) {
                LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {
                    items(getAllNotes.size) { index ->
                        NotesCardView(
                            title = getAllNotes[index].title.toString(),
                            noteText = getAllNotes[index].description.toString(),
                            backgroundColor = Color.Black,
                            onClick = {
                                val notesJsonString = Gson().toJson(getAllNotes[index])
                                navController.navigate(Screens.CreateNotesScreen.route + "?$NOTES_NAV_KEY=$notesJsonString")
                            }
                        )
                    }
                }
            }
        }
    }
}
