package org.example.moreinone.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.example.moreinone.R
import org.example.moreinone.common.SimpleText
import org.example.moreinone.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    SimpleText(text = stringResource(R.string.home_screen))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screens.SettingsScreen.route)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings Icon"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Card(
                onClick = {
                    // Navigate to TaskListScreen
                    navController.navigate(Screens.TaskListScreen.route)
                },
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(paddingValues)
            ) {
                SimpleText(
                    text = "TODO",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    )
}
