package org.example.moreinone.ui.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import org.example.moreinone.common.utils.SimpleText
import org.example.moreinone.ui.theme.BrightPurple
import org.example.moreinone.ui.theme.DarkPurple
import org.example.moreinone.ui.theme.LightGrey
import org.example.moreinone.ui.theme.Pink40
import org.example.moreinone.ui.theme.Pink80
import org.example.moreinone.ui.theme.Purple40
import org.example.moreinone.ui.theme.Purple80
import org.example.moreinone.ui.theme.PurpleGrey40
import org.example.moreinone.ui.theme.PurpleGrey80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorBottomSheet(sheetBackground: MutableIntState, onDismiss: () -> Unit) {
    val chooseNotesBackgroundList =
        listOf(
            Color.Black,
            Color.Gray,
            Color.LightGray,
            Color.Red,
            Color.Magenta,
            Purple80,
            Purple40,
            PurpleGrey80,
            PurpleGrey40,
            Pink80,
            Pink40,
            DarkPurple,
            BrightPurple,
            LightGrey
        )

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onDismiss()
        },
        containerColor = if (sheetBackground.intValue == Color.Black.toArgb())
            Color.DarkGray
        else
            Color(sheetBackground.intValue)
    ) {
        SimpleText(
            text = "Colour",
            textColor = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyRow(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 50.dp,
                top = 8.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            userScrollEnabled = true
        ) {
            items(chooseNotesBackgroundList.size) { index ->
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .border(
                            width = 2.dp,
                            if (chooseNotesBackgroundList[index].toArgb() == sheetBackground.intValue) {
                                Color.Cyan
                            } else {
                                Color.Unspecified
                            },
                            shape = CircleShape
                        )
                        .background(
                            color = chooseNotesBackgroundList[index],
                            shape = CircleShape
                        )
                        .clickable {
                            sheetBackground.intValue =
                                chooseNotesBackgroundList[index].toArgb()
                        },
                ) {
                    if (chooseNotesBackgroundList[index].toArgb() == sheetBackground.intValue) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "check mark",
                            modifier = Modifier
                                .size(35.dp)
                                .align(Alignment.Center),
                            tint = Color.Cyan
                        )
                    }
                }
            }
        }
    }
}
