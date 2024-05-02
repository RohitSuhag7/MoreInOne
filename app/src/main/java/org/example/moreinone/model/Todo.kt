package org.example.moreinone.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.example.moreinone.utils.Constants.TODO_TABLE

@Entity(tableName = TODO_TABLE)
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val task: String,
    val status: String,
    val createdOn: String,
    val isImportant: Boolean,
)