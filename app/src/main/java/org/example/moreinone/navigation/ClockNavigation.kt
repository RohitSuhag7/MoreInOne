package org.example.moreinone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.example.moreinone.ui.clock.alarm.AlarmScreen
import org.example.moreinone.ui.clock.stopwatch.StopwatchScreen

@Composable
fun ClockNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = ClockNavScreens.AlarmScreen.route
    ) {
        composable(route = ClockNavScreens.AlarmScreen.route) {
            AlarmScreen()
        }

        composable(route = ClockNavScreens.StopwatchScreen.route) {
            StopwatchScreen()
        }
    }
}
