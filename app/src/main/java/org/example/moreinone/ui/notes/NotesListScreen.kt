package org.example.moreinone.ui.notes

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import org.example.moreinone.R
import org.example.moreinone.common.utils.EmptyScreen
import org.example.moreinone.common.utils.MyFloatingActionButton
import org.example.moreinone.common.utils.MySearchBar
import org.example.moreinone.model.entities.Notes
import org.example.moreinone.navigation.Screens
import org.example.moreinone.utils.Constants.NOTES_NAV_KEY
import org.example.moreinone.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListScreen(navController: NavController) {

    val notesViewModel: NotesViewModel = hiltViewModel()

    val getAllNotes: List<Notes> by notesViewModel.getAllNotes.collectAsState(initial = emptyList())

    var searchStateHandler by remember { mutableStateOf(false) }

    var searchQuery by remember { mutableStateOf("") }

    // Using filter for searching in all notes
    val filteredNotes = getAllNotes.filter {
        it.title?.contains(searchQuery, ignoreCase = true)!! ||
                it.description?.contains(searchQuery, ignoreCase = true)!!
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            Crossfade(
                targetState = searchStateHandler,
                animationSpec = tween(500),
                label = "smooth animation"
            ) { state ->
                if (state) {
                    MySearchBar(
                        query = searchQuery,
                        onQueryChange = {
                            searchQuery = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 18.sp
                        ),
                        placeholderText = stringResource(id = R.string.search_your_notes),
                        leadingIconImageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        onClickLeadingIcon = {
                            searchQuery = ""
                            searchStateHandler = false
                        },
                        onClickTrailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                searchQuery = ""
                            } else {
                                searchStateHandler = false
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.LightGray,
                            focusedBorderColor = Color.White,
                            cursorColor = Color.White
                        ),
                        onKeyboardSearchClick = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    )
                } else {
                    TopAppBar(title = {
                        Text(
                            text = stringResource(R.string.notes),
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = FontFamily.Cursive
                        )
                    },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Black
                        ),
                        actions = {
                            IconButton(onClick = {
                                searchStateHandler = true
                            }) {
                                Icon(
                                    imageVector = Icons.Rounded.Search,
                                    contentDescription = "search icon",
                                    tint = Color.White,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                    )
                }
            }
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
                textStyle = TextStyle(fontSize = 28.sp, color = Color.White),
                paddingValues = paddingValues
            )
        } else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(top = 20.dp)
            ) {
                LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {
                    items(filteredNotes.size) { index ->
                        NotesCardView(
                            title = filteredNotes[index].title.toString(),
                            noteText = filteredNotes[index].description.toString(),
                            backgroundColor = Color(filteredNotes[index].backgroundColor!!),
                            onClick = {
                                val notesJsonString = Gson().toJson(filteredNotes[index])
                                navController.navigate(Screens.CreateNotesScreen.route + "?$NOTES_NAV_KEY=$notesJsonString")
                            }
                        )
                    }
                }
            }
        }
    }
}
