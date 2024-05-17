package org.example.moreinone.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import org.example.moreinone.R
import org.example.moreinone.common.EmptyScreen
import org.example.moreinone.common.SimpleText
import org.example.moreinone.common.mySnackbar
import org.example.moreinone.model.Todo
import org.example.moreinone.navigation.Screens
import org.example.moreinone.utils.Constants.TODO_NAV_KEY
import org.example.moreinone.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(navController: NavController) {

    val todoViewModel: TodoViewModel = hiltViewModel()
    val getAllTodos: List<Todo> by todoViewModel.getAllTodos.collectAsState(initial = emptyList())

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var dropDownMenuExpended by remember { mutableStateOf(false) }

    var isSorted by remember { mutableStateOf(false) }

    val mContext = LocalContext.current

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.todo_list)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        dropDownMenuExpended = !dropDownMenuExpended
                    }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "More Icon"
                        )
                    }

                    DropdownMenu(
                        expanded = dropDownMenuExpended,
                        onDismissRequest = { dropDownMenuExpended = false }) {
                        DropdownMenuItem(
                            text = { SimpleText(text = "Important") },
                            onClick = {
                                isSorted = true
                            })
                        DropdownMenuItem(
                            text = { SimpleText(text = "Unimportant") },
                            onClick = {
                                isSorted = false
                            })
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screens.TaskCreateScreen.route)
                },
                content = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                    )
                }
            )
        },
        content = { paddingValues ->
            if (getAllTodos.isEmpty()) {
                EmptyScreen(
                    text = stringResource(R.string.no_tasks),
                    paddingValues = paddingValues
                )
            } else {
                Column(modifier = Modifier.padding(paddingValues)) {
                    LazyColumn {
                        val listSorted =
                            if (isSorted)
                                getAllTodos.sortedByDescending { it.isImportant }
                            else
                                getAllTodos
                        items(listSorted.size) { index ->
                            ListCardView(
                                todo = listSorted[index],
                                todoViewModel = todoViewModel,
                                navController = navController,
                                onDelete = {
                                    todoViewModel.deleteTodo(todo = listSorted[index])
                                    mySnackbar(
                                        scope = scope,
                                        snackbarHostState = snackbarHostState,
                                        msg = mContext.getString(
                                            R.string.deleted,
                                            getAllTodos[index].taskName
                                        ),
                                        actionLabel = mContext.getString(R.string.undo),
                                        onAction = { todoViewModel.undoDeletedTodo() }
                                    )
                                }
                            )
                        }
                    }
                }

            }
        }
    )
}

@Composable
fun ListCardView(
    todo: Todo,
    todoViewModel: TodoViewModel,
    navController: NavController,
    onDelete: () -> Unit,
) {

    var taskStatusState by remember { mutableStateOf(todo.status ?: false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Checkbox(
                checked = taskStatusState,
                onCheckedChange = { taskStatusState = it }
            )
            if (!taskStatusState) {
                SimpleText(
                    modifier = Modifier.weight(1f),
                    text = todo.taskName ?: "",
                    textStyle = MaterialTheme.typography.titleMedium.plus(
                        TextStyle(fontWeight = FontWeight.Bold)
                    ),
                    maxLines = 1
                )
                todo.status = taskStatusState
                todoViewModel.insertTodo(todo)
            } else {
                SimpleText(
                    modifier = Modifier.weight(1f),
                    text = todo.taskName ?: "",
                    textStyle = MaterialTheme.typography.titleMedium.plus(
                        TextStyle(textDecoration = TextDecoration.LineThrough)
                    ),
                    maxLines = 1
                )
                todo.status = taskStatusState
                todoViewModel.insertTodo(todo)
            }
            if (todo.isImportant == true) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null
                )
            }
            IconButton(onClick = {
                val todoJsonString = Gson().toJson(todo)
                navController.navigate(Screens.TaskCreateScreen.route + "?$TODO_NAV_KEY=${todoJsonString}")
            }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit Icon",
                    tint = Color.Gray
                )
            }
            IconButton(onClick = {
                onDelete()
            }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Icon",
                    tint = Color.Red
                )
            }
        }
    }
}
