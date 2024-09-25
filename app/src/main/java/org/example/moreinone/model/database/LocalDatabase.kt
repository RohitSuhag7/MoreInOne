package org.example.moreinone.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.example.moreinone.model.dao.SettingsDao
import org.example.moreinone.model.entities.MoreSettings

@Database(entities = [MoreSettings::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun settingsDao(): SettingsDao
}
