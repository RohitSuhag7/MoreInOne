package org.example.moreinone.model.entities

import androidx.compose.runtime.mutableStateListOf
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.example.moreinone.utils.Constants.ALARM_TABLE

@Entity(tableName = ALARM_TABLE)
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val alarmLabel: String? = "",
    val alarmTime: String? = "",
    val alarmAmPm: String? = "",
    val isAlarmSet: Boolean? = false,
    val alarmDays: MutableList<String>? = mutableStateListOf()
)

class Converters {

    // Convert a String to MutableList<String> (JSON format)
    @TypeConverter
    fun fromStringToMutableList(value: String?): MutableList<String>? {
        val listType = object : TypeToken<MutableList<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    // Convert MutableList<String> to a String (JSON format)
    @TypeConverter
    fun fromMutableListToString(list: MutableList<String>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}
