package org.example.moreinone.common.biometricAuthentication

import android.app.Activity
import android.content.Intent
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.example.moreinone.R
import org.example.moreinone.common.dialog.CommonDialog
import org.example.moreinone.model.entities.MoreSettings
import org.example.moreinone.navigation.Navigate
import org.example.moreinone.viewmodel.MoreInOneViewModel

@Composable
fun BiometricResultsHandle(
    biometricPromptManager: BiometricPromptManager
) {
    val localContext = LocalContext.current
    val biometricResult by biometricPromptManager.promptResults.collectAsState(initial = null)

    val moreInOneViewModel: MoreInOneViewModel = hiltViewModel()
    val getSettings: MoreSettings? by moreInOneViewModel.getSettings.collectAsState(initial = MoreSettings())

    val openAlertDialog = remember { mutableStateOf(false) }

    fun updateAuthenticationValue() {
        // Update values in Settings Table
        moreInOneViewModel.insertSettings(
            MoreSettings(
                id = getSettings?.id ?: 0,
                isAuthenticate = false
            )
        )

        // Dismiss Dialog
        openAlertDialog.value = false
    }

    // For maintain composable for activity result
    val hasLaunchedEnrollment = remember { mutableStateOf(false) }

    val enrollLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK || result.resultCode == 3) {
                biometricPromptManager.showBiometricPrompt(
                    localContext.getString(R.string.authenticate),
                    localContext.getString(R.string.please_authenticate)
                )
            } else {
                openAlertDialog.value = true
            }
            hasLaunchedEnrollment.value = true
        })

    if (openAlertDialog.value) {
        CommonDialog(
            onDismissRequest = {
                updateAuthenticationValue()
            },
            confirmButton = {
                hasLaunchedEnrollment.value = false
                openAlertDialog.value = false
                biometricPromptManager.showBiometricPrompt(
                    localContext.getString(R.string.authenticate),
                    localContext.getString(R.string.please_authenticate)
                )
            },
            confirmButtonText = stringResource(R.string.set),
            dismissButtonText = stringResource(R.string.no),
            icon = Icons.Default.Warning,
            title = stringResource(id = R.string.authenticate),
            description = stringResource(id = R.string.do_you_want_authentication)
        )
    }

    biometricResult?.let { result ->
        when (result) {
            is BiometricResult.AuthenticationError -> {
                CommonDialog(
                    onDismissRequest = {
                        updateAuthenticationValue()
                    },
                    confirmButton = {
                        updateAuthenticationValue()
                    },
                    confirmButtonText = stringResource(R.string.ok),
                    icon = Icons.Default.Warning,
                    title = stringResource(id = R.string.error),
                    description = stringResource(id = R.string.error_description)
                )
            }

            BiometricResult.AuthenticationFailed -> {
                CommonDialog(
                    onDismissRequest = {
                        updateAuthenticationValue()
                    },
                    confirmButton = {
                        updateAuthenticationValue()
                    },
                    confirmButtonText = stringResource(R.string.ok),
                    icon = Icons.Default.Warning,
                    title = stringResource(id = R.string.error),
                    description = stringResource(id = R.string.error_description)
                )
            }

            BiometricResult.AuthenticationNotSet -> {
                if (!hasLaunchedEnrollment.value) {
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

                    hasLaunchedEnrollment.value = true
                }
            }

            BiometricResult.AuthenticationSuccess -> Navigate()

            BiometricResult.FeatureUnavailable -> {
                CommonDialog(
                    onDismissRequest = {
                        updateAuthenticationValue()
                    },
                    confirmButton = {
                        updateAuthenticationValue()
                    },
                    confirmButtonText = stringResource(R.string.ok),
                    icon = Icons.Default.Warning,
                    title = stringResource(id = R.string.error),
                    description = stringResource(id = R.string.feature_error_description)
                )
            }

            BiometricResult.HardwareUnavailable -> {
                CommonDialog(
                    onDismissRequest = {
                        updateAuthenticationValue()
                    },
                    confirmButton = {
                        updateAuthenticationValue()
                    },
                    confirmButtonText = stringResource(R.string.ok),
                    icon = Icons.Default.Warning,
                    title = stringResource(id = R.string.error),
                    description = stringResource(id = R.string.feature_error_description)
                )
            }
        }
    }
}
