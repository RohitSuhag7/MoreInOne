package org.example.moreinone.repository

import kotlinx.coroutines.flow.Flow
import org.example.moreinone.model.entities.Todo
import org.example.moreinone.model.dao.TodoDao
import javax.inject.Inject

class TodoRepository @Inject constructor(private val todoDao: TodoDao) {

    suspend fun insertTodo(todo: Todo): Long = todoDao.insertTodo(todo)

    suspend fun deleteTodo(todo: Todo) = todoDao.deleteTodo(todo)

    fun getAllTodos(): Flow<List<Todo>> = todoDao.getAllTodos()
}
