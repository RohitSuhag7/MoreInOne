package org.example.moreinone.ui.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import org.example.moreinone.R
import org.example.moreinone.common.utils.SimpleText
import org.example.moreinone.common.utils.SimpleTextField
import org.example.moreinone.common.utils.textFieldColors
import org.example.moreinone.model.entities.Notes
import org.example.moreinone.navigation.Screens
import org.example.moreinone.ui.theme.BrightPurple
import org.example.moreinone.ui.theme.DarkPurple
import org.example.moreinone.ui.theme.LightGrey
import org.example.moreinone.ui.theme.Pink40
import org.example.moreinone.ui.theme.Pink80
import org.example.moreinone.ui.theme.Purple40
import org.example.moreinone.ui.theme.Purple80
import org.example.moreinone.ui.theme.PurpleGrey40
import org.example.moreinone.ui.theme.PurpleGrey80
import org.example.moreinone.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNotesScreen(navController: NavController, notesJsonString: String?) {

    val note = Gson().fromJson(notesJsonString, Notes::class.java)

    val title = remember { mutableStateOf(note?.title ?: "") }
    val notesDesc = remember { mutableStateOf(note?.description ?: "") }
    val notesBackground =
        remember { mutableIntStateOf(note?.backgroundColor ?: Color.Black.toArgb()) }

    val scrollState = rememberScrollState()

    var dropDownMenuExpended by remember { mutableStateOf(false) }

    val notesViewModel: NotesViewModel = hiltViewModel()

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    val chooseNotesBackgroundList =
        listOf(
            Color.Black,
            Color.Gray,
            Color.LightGray,
            Color.Red,
            Color.Magenta,
            Color.Cyan,
            Purple80,
            Purple40,
            PurpleGrey80,
            PurpleGrey40,
            Pink80,
            Pink40,
            DarkPurple,
            BrightPurple,
            LightGrey
        )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(notesBackground.intValue)
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        if (title.value.isNotEmpty() || notesDesc.value.isNotEmpty()) {
                            saveNotes(
                                id = note?.id,
                                title = title.value,
                                notesDesc = notesDesc.value,
                                notesBackground = notesBackground.intValue,
                                notesViewModel = notesViewModel,
                                navController = navController
                            )
                        } else {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        showBottomSheet = true
                    }) {
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
                                    saveNotes(
                                        id = note?.id,
                                        title = title.value,
                                        notesDesc = notesDesc.value,
                                        notesBackground = notesBackground.intValue,
                                        notesViewModel = notesViewModel,
                                        navController = navController
                                    )
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
                                note?.let {
                                    notesViewModel.deleteNote(note)
                                }
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
                .background(Color(notesBackground.intValue))
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
                textFieldColors = textFieldColors(textFieldColor = Color(notesBackground.intValue))
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
                textFieldColors = textFieldColors(textFieldColor = Color(notesBackground.intValue))
            )
        }

        // Show Bottom Sheet drawer when click on Choose notes background color icon
        if (showBottomSheet) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    showBottomSheet = false
                },
                containerColor = Color.DarkGray
            ) {
                SimpleText(
                    text = "Colour",
                    textColor = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                LazyRow(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 50.dp,
                        top = 8.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    userScrollEnabled = true
                ) {
                    items(chooseNotesBackgroundList.size) { index ->
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(
                                    color = chooseNotesBackgroundList[index],
                                    shape = CircleShape
                                )
                                .clickable {
                                    notesBackground.intValue =
                                        chooseNotesBackgroundList[index].toArgb()
                                },
                        )
                    }
                }
            }
        }
    }
}

private fun saveNotes(
    id: Int?,
    title: String,
    notesDesc: String,
    notesBackground: Int,
    notesViewModel: NotesViewModel,
    navController: NavController
) {
    notesViewModel.insertNote(
        Notes(
            id = id ?: 0,
            title = title,
            description = notesDesc,
            backgroundColor = notesBackground
        )
    )

    // Navigate to NotesList Screen
    navController.navigate(Screens.NotesListScreen.route)
}
