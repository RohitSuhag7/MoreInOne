package org.example.moreinone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.moreinone.ui.TaskCreateScreen
import org.example.moreinone.ui.TaskListScreen

@Composable
fun Navigate() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.TaskListScreen.route) {
        composable(route = Screens.TaskListScreen.route) {
            TaskListScreen(navController)
        }
        composable(route = Screens.TaskCreateScreen.route) {
            TaskCreateScreen(navController)
        }
    }
}
