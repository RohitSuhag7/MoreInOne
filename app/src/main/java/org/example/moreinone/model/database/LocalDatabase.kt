package org.example.moreinone.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.example.moreinone.model.dao.AlarmDao
import org.example.moreinone.model.dao.NotesDao
import org.example.moreinone.model.dao.SettingsDao
import org.example.moreinone.model.dao.TodoDao
import org.example.moreinone.model.entities.Alarm
import org.example.moreinone.model.entities.Converters
import org.example.moreinone.model.entities.MoreSettings
import org.example.moreinone.model.entities.Notes
import org.example.moreinone.model.entities.Todo

@Database(
    entities = [MoreSettings::class, Todo::class, Notes::class, Alarm::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun settingsDao(): SettingsDao

    abstract fun todoDao(): TodoDao

    abstract fun notesDao(): NotesDao

    abstract fun alarmDao(): AlarmDao
}
