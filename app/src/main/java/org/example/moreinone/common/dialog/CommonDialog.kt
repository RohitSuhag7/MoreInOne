package org.example.moreinone.common.dialog

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.moreinone.common.utils.SimpleText

@Composable
fun CommonDialog(
    onDismissRequest: () -> Unit,
    confirmButton: () -> Unit,
    confirmButtonText: String,
    dismissButtonText: String? = null,
    icon: ImageVector,
    title: String,
    description: String,
) {
    AlertDialog(
        icon = {
            Icon(imageVector = icon, contentDescription = "", modifier = Modifier.size(50.dp))
        },
        title = {
            SimpleText(
                text = title,
                textColor = Color.Black,
                textStyle = TextStyle(fontSize = 20.sp)
            )
        },
        text = {
            SimpleText(text = description)
        },
        onDismissRequest = {
            onDismissRequest()
        }, confirmButton = {
            TextButton(onClick = {
                confirmButton()
            }) {
                SimpleText(text = confirmButtonText)
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismissRequest()
            }) {
                SimpleText(text = dismissButtonText ?: "")
            }
        }
    )
}
