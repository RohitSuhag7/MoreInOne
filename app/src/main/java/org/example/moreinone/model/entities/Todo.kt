package org.example.moreinone.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.example.moreinone.utils.Constants.TODO_TABLE

@Entity(tableName = TODO_TABLE)
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val taskName: String? = "",
    val taskDesc: String? = "",
    val createdOn: String? = "",
    val createdTime: String? = "",
    val isImportant: Boolean? = false,
    var status: Boolean? = false,
)
