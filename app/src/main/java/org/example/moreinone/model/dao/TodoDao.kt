package org.example.moreinone.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.moreinone.model.entities.Todo

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo): Long

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM TODO_TABLE")
    fun getAllTodos(): Flow<List<Todo>>
}
