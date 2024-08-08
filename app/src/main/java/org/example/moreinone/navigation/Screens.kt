package org.example.moreinone.navigation

import org.example.moreinone.utils.Constants

sealed class Screens(val route: String) {
    data object TaskListScreen : Screens(Constants.TASK_LIST_SCREEN)
    data object TaskCreateScreen : Screens(Constants.TASK_CREATE_SCREEN)
    data object SettingsScreen : Screens(Constants.SETTINGS_SCREEN)
}
