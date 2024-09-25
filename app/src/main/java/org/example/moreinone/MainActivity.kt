package org.example.moreinone

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.example.moreinone.model.entities.MoreSettings
import org.example.moreinone.navigation.Navigate
import org.example.moreinone.ui.theme.MoreInOneTheme
import org.example.moreinone.common.biometricAuthentication.BiometricPromptManager
import org.example.moreinone.common.biometricAuthentication.BiometricResultsHandle
import org.example.moreinone.common.reminder.NotificationHandler
import org.example.moreinone.viewmodel.MoreInOneViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val promptManager by lazy { BiometricPromptManager(this) }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoreInOneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val moreInOneViewModel: MoreInOneViewModel = hiltViewModel()
                    val getSettings: MoreSettings? by moreInOneViewModel.getSettings.collectAsState(initial = MoreSettings())

                    if (getSettings?.isAuthenticate != null && getSettings?.isAuthenticate == true) {
                        promptManager.showBiometricPrompt(
                            stringResource(R.string.authenticate),
                            stringResource(
                                R.string.please_authenticate
                            )
                        )
                        BiometricResultsHandle(promptManager)
                    } else {
                        Navigate()
                    }

                    // Create Notification Channel
                    NotificationHandler(this).createNotificationChannel()
                }
            }
        }
    }
}
