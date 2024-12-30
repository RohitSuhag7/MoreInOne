package org.example.moreinone.model.entities

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.example.moreinone.utils.Constants.NOTES_TABLE

@Entity(tableName = NOTES_TABLE)
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String? = "",
    val description: String? = "",
    val backgroundColor: Int? = Color.Black.toArgb()
)
