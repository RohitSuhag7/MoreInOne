package org.example.moreinone.common.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MyFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    containerColor: Color = MaterialTheme.colorScheme.secondary,
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(8.dp),
    imageVector: ImageVector,
    tint: Color = Color.White
) {
    FloatingActionButton(
        onClick = { onClick() },
        modifier = modifier,
        shape = shape,
        containerColor = containerColor,
        elevation = elevation
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = tint
        )
    }
}
