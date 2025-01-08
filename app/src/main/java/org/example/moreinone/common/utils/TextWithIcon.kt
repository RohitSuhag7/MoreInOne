package org.example.moreinone.common.utils

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.sp

@Composable
fun dynamicAnnotatedString(label: String): AnnotatedString {
    return buildAnnotatedString {
        appendInlineContent("icon", "[icon]")
        append("  $label")
    }
}

@Composable
fun inlineContent(painterIcon: Painter): Map<String, InlineTextContent> {
    return mapOf(
        "icon" to InlineTextContent(
            placeholder = Placeholder(
                width = 25.sp,
                height = 25.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Icon(
                painter = painterIcon,
                contentDescription = "icon",
                tint = Color.White
            )
        }
    )
}
