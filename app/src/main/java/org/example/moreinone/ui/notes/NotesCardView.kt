package org.example.moreinone.ui.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.moreinone.common.utils.SimpleText

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesCardView(
    title: String,
    noteText: String,
    backgroundColor: Color,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    cardBorderColor: Color = Color.Gray
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(
            width = 2.dp,
            color = cardBorderColor
        ),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = {
                    onLongClick()
                }
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = backgroundColor)
                .padding(8.dp)
        ) {
            AnimatedVisibility(visible = title.isNotEmpty()) {
                SimpleText(
                    text = title,
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            AnimatedVisibility(visible = noteText.isNotEmpty()) {
                SimpleText(
                    text = noteText,
                    textStyle = TextStyle(color = Color.White, fontSize = 15.sp),
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 4.dp)
                )
            }
        }
    }
}
