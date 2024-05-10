package org.example.moreinone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.example.moreinone.ui.TaskCreateScreen
import org.example.moreinone.ui.TaskListScreen
import org.example.moreinone.utils.Constants.TODO_NAV_KEY

@Composable
fun Navigate() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.TaskListScreen.route) {
        composable(route = Screens.TaskListScreen.route) {
            TaskListScreen(navController = navController)
        }
        composable(
            route = Screens.TaskCreateScreen.route + "?$TODO_NAV_KEY={$TODO_NAV_KEY}",
            arguments = listOf(
                navArgument(TODO_NAV_KEY) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { navBackStackEntry ->
            TaskCreateScreen(
                navController = navController,
                navBackStackEntry.arguments?.getString(TODO_NAV_KEY)
            )
        }
    }
}
