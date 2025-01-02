package org.example.moreinone.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.example.moreinone.R
import org.example.moreinone.utils.Constants

sealed class Screens(val route: String) {

    data object HomeScreen : Screens(Constants.HOME_SCREEN)

    data object TaskListScreen : Screens(Constants.TASK_LIST_SCREEN)

    data object TaskCreateScreen : Screens(Constants.TASK_CREATE_SCREEN)

    data object SettingsScreen : Screens(Constants.SETTINGS_SCREEN)

    data object CalculatorScreen : Screens(Constants.CALCULATOR_SCREEN)

    data object NotesListScreen : Screens(Constants.NOTES_LIST_SCREEN)

    data object CreateNotesScreen : Screens(Constants.NOTES_CREATE_SCREEN)

    data object ClockScreen : Screens(Constants.CLOCK_SCREEN)
}

sealed class ClockNavScreens(
    val route: String,
    @DrawableRes val icons: Int,
    @StringRes val label: Int
) {
    data object AlarmScreen : ClockNavScreens(
        Constants.ALARM_SCREEN, R.drawable.ic_alarm,
        R.string.alarm
    )

    data object StopwatchScreen : ClockNavScreens(
        Constants.STOPWATCH_SCREEN,
        R.drawable.ic_stopwatch,
        R.string.stopwatch
    )
}
