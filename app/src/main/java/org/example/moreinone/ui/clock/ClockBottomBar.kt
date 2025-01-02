package org.example.moreinone.ui.clock

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.example.moreinone.common.utils.SimpleText
import org.example.moreinone.navigation.ClockNavigation
import org.example.moreinone.navigation.ClockNavScreens

@Composable
fun ClockBottomNavigation() {
    val navController = rememberNavController()

    val screens = mutableListOf(
        ClockNavScreens.AlarmScreen,
        ClockNavScreens.StopwatchScreen
    )

    Scaffold(
        bottomBar = {
            BottomAppBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                screens.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route)
                        }, icon = {
                            Icon(painterResource(id = item.icons), contentDescription = null)
                        },
                        label = {
                            SimpleText(text = stringResource(id = item.label))
                        }
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ClockNavigation(navController = navController)
        }
    }
}
