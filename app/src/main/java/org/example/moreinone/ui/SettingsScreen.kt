package org.example.moreinone.ui

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.example.moreinone.common.SimpleText
import org.example.moreinone.model.entities.MoreSettings
import org.example.moreinone.viewmodel.MoreInOneViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {

    val moreInOneViewModel: MoreInOneViewModel = hiltViewModel()

    val getSettings: MoreSettings by moreInOneViewModel.getSettings.collectAsState(initial = MoreSettings())

    var isAuthenticate by remember { mutableStateOf(getSettings.isAuthenticate ?: false) }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Settings") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
    }) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                SimpleText(
                    text = "Authentication",
                    textColor = Color.Black,
                    modifier = Modifier.padding(12.dp)
                )
                Switch(
                    checked = isAuthenticate,
                    onCheckedChange = {
                        isAuthenticate = it
                    }
                )
            }
        }

        // Insert values in Settings Table
        moreInOneViewModel.insertSettings(
            MoreSettings(
                id = getSettings.id,
                isAuthenticate = isAuthenticate
            )
        )
    }
}
