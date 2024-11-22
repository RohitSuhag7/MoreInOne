package org.example.moreinone.ui.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.moreinone.ui.theme.BrightPurple
import org.example.moreinone.ui.theme.Purple40
import org.example.moreinone.ui.theme.PurpleGrey40
import org.example.moreinone.ui.theme.PurpleGrey80

@Composable
fun CalculatorScreen() {
    val buttonSpacing = 8.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth(),
                reverseLayout = true,
            ) {
                item {
                    Text(
                        text = "|",
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp, horizontal = 8.dp),
                        fontWeight = FontWeight.Light,
                        fontSize = 80.sp,
                        color = Color.White,
                        maxLines = 1
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black),
                verticalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    color = Color.Gray
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    CalculatorButton(
                        symbol = "AC",
                        color = BrightPurple,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    )
                    CalculatorButton(
                        symbol = "(",
                        color = PurpleGrey80,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    )
                    CalculatorButton(
                        symbol = ")",
                        color = PurpleGrey80,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    )
                    CalculatorButton(
                        symbol = "÷",
                        color = PurpleGrey80,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    CalculatorButton(
                        symbol = "7",
                        color = PurpleGrey40,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    )
                    CalculatorButton(
                        symbol = "8",
                        color = PurpleGrey40,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    )
                    CalculatorButton(
                        symbol = "9",
                        color = PurpleGrey40,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    )
                    CalculatorButton(
                        symbol = "×",
                        color = PurpleGrey80,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    CalculatorButton(
                        symbol = "4",
                        color = PurpleGrey40,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    )
                    CalculatorButton(
                        symbol = "5",
                        color = PurpleGrey40,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    )
                    CalculatorButton(
                        symbol = "6",
                        color = PurpleGrey40,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    )
                    CalculatorButton(
                        symbol = "-",
                        color = PurpleGrey80,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    CalculatorButton(
                        symbol = "1",
                        color = PurpleGrey40,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    )
                    CalculatorButton(
                        symbol = "2",
                        color = PurpleGrey40,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    )
                    CalculatorButton(
                        symbol = "3",
                        color = PurpleGrey40,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    )
                    CalculatorButton(
                        symbol = "+",
                        color = PurpleGrey80,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    CalculatorButton(
                        symbol = "0",
                        color = PurpleGrey40,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    )
                    CalculatorButton(
                        symbol = ".",
                        color = PurpleGrey40,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    )
                    CalculatorButton(
                        symbol = "Del",
                        color = BrightPurple,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    )
                    CalculatorButton(
                        symbol = "=",
                        color = Purple40,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    )
                }
            }

        }
    }
}

@Composable
fun CalculatorButton(
    symbol: String,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    textStyle: TextStyle = TextStyle()
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(color)
            .then(modifier)
    ) {
        Text(
            text = symbol,
            style = textStyle,
            fontSize = 36.sp,
            color = Color.White
        )
    }
}
