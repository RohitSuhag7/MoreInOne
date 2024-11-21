package org.example.moreinone.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.example.moreinone.R
import org.example.moreinone.common.SimpleText
import org.example.moreinone.navigation.Screens
import org.example.moreinone.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val applicationOptionsList = listOf("TODO", "Calculator", "Other")
    val colorsList =
        listOf(Color.Cyan, Color.Yellow, Color.Magenta, Color.Gray, Color.Blue, Color.Green)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.home_screen))
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
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                modifier = Modifier.padding(paddingValues)
            ) {
                items(applicationOptionsList.size) { index ->
                    Card(
                        onClick = {
                            when (applicationOptionsList[index]) {
                                Constants.TODO_APP -> {
                                    // Navigate to TaskListScreen
                                    navController.navigate(Screens.TaskListScreen.route)
                                }

                                Constants.CALCULATOR_APP -> {
                                    // TODO
                                }

                                Constants.OTHER_APP -> {
                                    // TODO
                                }
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        border = BorderStroke(width = 2.dp, color = Color.Black),
                        colors = CardDefaults.cardColors(containerColor = colorsList[index]),
                        modifier = Modifier
                            .width(100.dp)
                            .height(150.dp)
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            SimpleText(
                                text = applicationOptionsList[index],
                                textStyle = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    )
}
