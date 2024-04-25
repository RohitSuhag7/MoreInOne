package org.example.moreinone.utils

import java.text.SimpleDateFormat
import java.util.Date

fun convertLongToTime(time: Long): String {
    val date = Date(time)
    return SimpleDateFormat("dd-MM-yyyy").format(date)
}
