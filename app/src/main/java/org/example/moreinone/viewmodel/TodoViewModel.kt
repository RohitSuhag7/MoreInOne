package org.example.moreinone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.moreinone.model.entities.Todo
import org.example.moreinone.repository.TodoRepository
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val todoRepository: TodoRepository) : ViewModel() {

    private var deletedTodo: Todo? = null

    val getAllTodos = todoRepository.getAllTodos()

    fun insertTodo(todo: Todo, onResult: ((Long) -> Unit)? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val insertedId = todoRepository.insertTodo(todo)
            withContext(Dispatchers.Main) {
                onResult?.invoke(insertedId)
            }
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
}
