package org.example.moreinone.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertLongToTime(time: Long): String {
    val date = Date(time)
    return SimpleDateFormat("dd MMM, yyyy", Locale.ROOT).format(date)
}

fun timeFormatter(time: Long): String {
    val date = Date(time)
    return SimpleDateFormat("HH:mm", Locale.ROOT).format(date)
}

@OptIn(ExperimentalMaterial3Api::class)
object DisablePastDates : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis >= (System.currentTimeMillis() - Constants.ONE_DAY_TIME)
    }
}
