package org.example.moreinone.common.biometricAuthentication

import android.app.Activity
import android.content.Intent
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import org.example.moreinone.R
import org.example.moreinone.navigation.Navigate

@Composable
fun BiometricResultsHandle(
    biometricPromptManager: BiometricPromptManager
) {
    val localContext = LocalContext.current
    val biometricResult by biometricPromptManager.promptResults.collectAsState(initial = null)

    val enrollLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK || result.resultCode == 3) {
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
