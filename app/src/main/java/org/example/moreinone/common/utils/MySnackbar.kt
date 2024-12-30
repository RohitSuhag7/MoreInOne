package org.example.moreinone.common.utils

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun mySnackbar(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    msg: String,
    actionLabel: String,
    onAction: () -> Unit
) {
    scope.launch {
        snackbarHostState.currentSnackbarData?.dismiss()
        val snackbarResult = snackbarHostState.showSnackbar(
            message = msg,
            actionLabel = actionLabel,
            duration = SnackbarDuration.Short
        )

        when (snackbarResult) {
            SnackbarResult.ActionPerformed -> {
                onAction()
            }

            SnackbarResult.Dismissed -> {}
        }
    }
}
