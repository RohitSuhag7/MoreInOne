package org.example.moreinone.ui.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.example.moreinone.R
import org.example.moreinone.common.utils.SimpleText
import org.example.moreinone.common.utils.SimpleTextField
import org.example.moreinone.model.entities.Notes
import org.example.moreinone.navigation.Screens
import org.example.moreinone.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNotesScreen(navController: NavController) {

    val title = remember { mutableStateOf("") }
    val notesDesc = remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    var dropDownMenuExpended by remember { mutableStateOf(false) }

    val notesViewModel: NotesViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.color_palette),
                            contentDescription = "Color Change Icon",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(25.dp)
                        )
                    }

                    IconButton(onClick = {
                        dropDownMenuExpended = !dropDownMenuExpended
                    }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More Icon",
                            tint = Color.White
                        )
                    }

                    DropdownMenu(
                        expanded = dropDownMenuExpended,
                        onDismissRequest = { dropDownMenuExpended = false }) {
                        DropdownMenuItem(
                            text = { SimpleText(text = stringResource(id = R.string.save)) },
                            onClick = {
                                if (title.value.isNotEmpty() || notesDesc.value.isNotEmpty()) {
                                    notesViewModel.insertNote(
                                        Notes(
                                            title = title.value,
                                            description = notesDesc.value
                                        )
                                    )

                                    // Navigate to NotesList Screen
                                    navController.navigate(Screens.NotesListScreen.route)
                                }
                            })

                        HorizontalDivider(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp)
                                .border(width = 1.dp, color = Color.Black)
                        )

                        DropdownMenuItem(
                            text = { SimpleText(text = stringResource(id = R.string.delete)) },
                            onClick = {
                                navController.popBackStack()
                            })
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .verticalScroll(scrollState)
                .padding(paddingValues)
        ) {
            SimpleTextField(
                textValue = title.value,
                onValueChanges = {
                    title.value = it
                },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                ),
                hintText = "Title",
                placeHolderFontSize = 25.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                textFieldColors = textFieldColors()
            )
            SimpleTextField(
                textValue = notesDesc.value,
                onValueChanges = {
                    notesDesc.value = it
                },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp
                ),
                hintText = "Note",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                textFieldColors = textFieldColors()
            )
        }
    }
}

@Composable
fun textFieldColors(): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedContainerColor = Color.Black,
        unfocusedContainerColor = Color.Black,
        disabledContainerColor = Color.Black,
        cursorColor = Color.White,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )
}
