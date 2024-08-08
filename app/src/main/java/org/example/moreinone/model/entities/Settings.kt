package org.example.moreinone.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.example.moreinone.utils.Constants.SETTINGS_TABLE

@Entity(tableName = SETTINGS_TABLE)
data class Settings(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val isAuthenticate: Boolean? = false
)
