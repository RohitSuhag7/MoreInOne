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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.example.moreinone.common.SimpleText
import org.example.moreinone.model.Todo
import org.example.moreinone.navigation.Screens
import org.example.moreinone.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(navController: NavController) {

    val todoViewModel: TodoViewModel = hiltViewModel()
    val getAllTodos: List<Todo> by todoViewModel.getAllTodos.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo List") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
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
            Column(modifier = Modifier.padding(paddingValues)) {
                TodoLazyColumn(
                    todo = getAllTodos,
                    todoViewModel = todoViewModel,
                    navController = navController
                )
            }
        }
    )
}

@Composable
fun TodoLazyColumn(todo: List<Todo>, todoViewModel: TodoViewModel, navController: NavController) {
    LazyColumn {
        items(todo.size) { index ->
            ListCardView(todo[index], todoViewModel, navController)
        }
    }
}

@Composable
fun ListCardView(todo: Todo, todoViewModel: TodoViewModel, navController: NavController) {
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
            IconButton(onClick = {
                navController.navigate(Screens.TaskCreateScreen.route)
            }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit Icon",
                    tint = Color.Gray
                )
            }
            IconButton(onClick = {
                todoViewModel.deleteTodo(todo)
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
