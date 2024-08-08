package org.example.moreinone.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.example.moreinone.model.dao.TodoDao
import org.example.moreinone.model.entities.Todo

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}
