package org.example.moreinone.ui.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotesCardView(
    noteText: String,
    onTextChange: (String) -> Unit,
    backgroundColor: Color
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(backgroundColor)
        ) {
            BasicTextField(
                value = TextFieldValue(noteText),
                onValueChange = { value -> onTextChange(value.text) },
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 18.sp,
                    color = Color.Black
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
