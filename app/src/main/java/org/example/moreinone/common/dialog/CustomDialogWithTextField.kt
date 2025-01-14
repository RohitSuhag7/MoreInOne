package org.example.moreinone.common.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.example.moreinone.R
import org.example.moreinone.common.utils.SimpleText

@Composable
fun CustomDialogWithTextField(
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
    textValue: String,
    onTextValueChange: (String) -> Unit,
    labelValue: String
) {
    val focusRequester = FocusRequester()
    var textFieldValue by remember { mutableStateOf(TextFieldValue(textValue)) }

    // Trigger Auto Focus text field and Auto select text
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()

        textFieldValue = textFieldValue.copy(selection = TextRange(0, textValue.length))
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = { value ->
                        textFieldValue = value
                        onTextValueChange(value.text)
                    },
                    label = {
                        Text(text = labelValue)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Cyan
                    ),
                    modifier = Modifier.focusRequester(focusRequester)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismissRequest) {
                        SimpleText(text = stringResource(id = R.string.cancel))
                    }

                    TextButton(onClick = onConfirmClick) {
                        SimpleText(text = stringResource(id = R.string.ok))
                    }
                }
            }
        }
    }
}
