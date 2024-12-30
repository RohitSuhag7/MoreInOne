package org.example.moreinone.ui.notes

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.moreinone.R
import org.example.moreinone.common.utils.SimpleText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LongClickTopAppVar(
    titleText: String = "",
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    TopAppBar(
        title = {
            SimpleText(
                text = titleText,
                textColor = Color.White,
                textStyle = TextStyle(fontSize = 20.sp)
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                onCancelClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "cancel icon",
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black
        ),
        actions = {
            IconButton(onClick = {
                onDeleteClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "delete note icon",
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(onSearchClick: () -> Unit) {
    TopAppBar(title = {
        Text(
            text = stringResource(R.string.notes),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontFamily = FontFamily.Cursive
        )
    },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black
        ),
        actions = {
            IconButton(onClick = {
                onSearchClick()
            }) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "search icon",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    )
}
