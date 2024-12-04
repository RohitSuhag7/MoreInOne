package org.example.moreinone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.example.moreinone.ui.HomeScreen
import org.example.moreinone.ui.SettingsScreen
import org.example.moreinone.ui.calculator.CalculatorScreen
import org.example.moreinone.ui.notes.CreateNotesScreen
import org.example.moreinone.ui.notes.NotesListScreen
import org.example.moreinone.ui.todo.TaskCreateScreen
import org.example.moreinone.ui.todo.TaskListScreen
import org.example.moreinone.utils.Constants.NOTES_NAV_KEY
import org.example.moreinone.utils.Constants.TODO_NAV_KEY

@Composable
fun Navigate() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.HomeScreen.route) {
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

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

        composable(route = Screens.SettingsScreen.route) {
            SettingsScreen(navController)
        }

        composable(route = Screens.CalculatorScreen.route) {
            CalculatorScreen()
        }

        composable(route = Screens.NotesListScreen.route) {
            NotesListScreen(navController)
        }

        composable(route = Screens.CreateNotesScreen.route + "?$NOTES_NAV_KEY={$NOTES_NAV_KEY}",
            arguments = listOf(
                navArgument(NOTES_NAV_KEY) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { navBackStackEntry ->
            CreateNotesScreen(
                navController = navController,
                navBackStackEntry.arguments?.getString(NOTES_NAV_KEY)
            )
        }
    }
}
