package org.example.moreinone.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.example.moreinone.model.dao.SettingsDao
import org.example.moreinone.model.dao.TodoDao
import org.example.moreinone.model.entities.MoreSettings
import org.example.moreinone.model.entities.Todo

@Database(entities = [MoreSettings::class, Todo::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun settingsDao(): SettingsDao

    abstract fun todoDao(): TodoDao
}
