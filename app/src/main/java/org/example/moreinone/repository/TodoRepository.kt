package org.example.moreinone.repository

import kotlinx.coroutines.flow.Flow
import org.example.moreinone.model.Todo
import org.example.moreinone.model.TodoDao

class TodoRepository(private val todoDao: TodoDao) {

    suspend fun insertTodo(todo: Todo) = todoDao.insertTodo(todo)

    suspend fun updateTodo(todo: Todo) = todoDao.updateTodo(todo)

    suspend fun deleteTodo(todo: Todo) = todoDao.deleteTodo(todo)

    suspend fun getTodoById(id: Int): Todo = todoDao.getTodoById(id)

    fun getAllTodos(): Flow<List<Todo>> = todoDao.getAllTodos()
}
