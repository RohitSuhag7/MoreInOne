package org.example.moreinone.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.moreinone.model.Todo
import org.example.moreinone.repository.TodoRepository
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val todoRepository: TodoRepository) : ViewModel() {

    private var todo by mutableStateOf(Todo(0, "", "", "", false, false))

    private var deletedTodo: Todo? = null

    val getAllTodos = todoRepository.getAllTodos()

    fun insertTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository.insertTodo(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            deletedTodo = todo
            todoRepository.deleteTodo(todo)
        }
    }

    fun undoDeletedTodo() {
        deletedTodo?.let { todo ->
            viewModelScope.launch(Dispatchers.IO) {
                todoRepository.insertTodo(todo)
            }
        }
    }

    fun getTodoById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository.getTodoById(id)
        }
    }

    fun updateTask(newValue: String) {
        todo = todo.copy(taskName = newValue)
    }

    fun updateIsImportant(newValue: Boolean) {
        todo = todo.copy(isImportant = newValue)
    }
}
