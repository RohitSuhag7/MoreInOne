package org.example.moreinone

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.example.moreinone.model.entities.MoreSettings
import org.example.moreinone.navigation.Navigate
import org.example.moreinone.ui.theme.MoreInOneTheme
import org.example.moreinone.utils.biometricAuthentication.BiometricPromptManager
import org.example.moreinone.utils.biometricAuthentication.BiometricResult
import org.example.moreinone.utils.reminder.NotificationHandler
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

@Composable
private fun BiometricResultsHandle(
    biometricPromptManager: BiometricPromptManager
) {
    val localContext = LocalContext.current
    val biometricResult by biometricPromptManager.promptResults.collectAsState(initial = null)

    val enrollLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                biometricPromptManager.showBiometricPrompt(
                    localContext.getString(R.string.authenticate),
                    localContext.getString(R.string.please_authenticate)
                )
            } else {
                Toast.makeText(
                    localContext,
                    "Add Dialog for Negative Scenario",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

    biometricResult?.let { result ->
        when (result) {
            is BiometricResult.AuthenticationError -> {
                Toast.makeText(
                    localContext,
                    result.error,
                    Toast.LENGTH_LONG
                ).show()
            }

            BiometricResult.AuthenticationFailed -> {
                Toast.makeText(
                    localContext,
                    "Authentication Failed",
                    Toast.LENGTH_LONG
                ).show()
            }

            BiometricResult.AuthenticationNotSet -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(
                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                        )
                    }
                    enrollLauncher.launch(enrollIntent)
                } else {
                    val enrollIntent = Intent(Settings.ACTION_SECURITY_SETTINGS)
                    enrollLauncher.launch(enrollIntent)
                }
            }

            BiometricResult.AuthenticationSuccess -> Navigate()

            BiometricResult.FeatureUnavailable -> {
                Toast.makeText(
                    localContext,
                    "Feature unavailable",
                    Toast.LENGTH_LONG
                ).show()
            }

            BiometricResult.HardwareUnavailable -> {
                Toast.makeText(
                    localContext,
                    "Hardware unavailable",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
