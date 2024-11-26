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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import org.example.moreinone.ui.theme.BrightPurple
import org.example.moreinone.ui.theme.Purple40
import org.example.moreinone.ui.theme.PurpleGrey40
import org.example.moreinone.ui.theme.PurpleGrey80
import org.example.moreinone.viewmodel.CalculatorViewModel

@Composable
fun CalculatorScreen() {
    val buttonSpacing = 8.dp

    val calculatorViewModel: CalculatorViewModel = hiltViewModel()
    val expression = calculatorViewModel.expression

    // Calculator Buttons
    val calculatorButtons = listOf(
        listOf("AC", "(", ")", "÷"),
        listOf("7", "8", "9", "×"),
        listOf("4", "5", "6", "-"),
        listOf("1", "2", "3", "+"),
        listOf("0", ".", "⌫", "=")
    )

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
                        text = expression.value.ifEmpty { "|" },
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

                calculatorButtons.forEach { row ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        row.forEach { button ->
                            CalculatorButton(
                                symbol = button,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1f),
                                color = when (button) {
                                    in "AC, ⌫" -> BrightPurple
                                    "=" -> Purple40
                                    in "÷, ×, -, +, (, )" -> PurpleGrey80
                                    else -> PurpleGrey40
                                },
                                onClick = {
                                    when (button) {
                                        "AC" -> calculatorViewModel.clear()
                                        "=" -> if (expression.value.isNotEmpty() && expression.value.any { it.isDigit() }) calculatorViewModel.evaluateExpression()
                                        in "÷, ×, -, +" -> if (expression.value.isNotEmpty()) calculatorViewModel.append(button)
                                        ")" -> if (expression.value.contains('(')) calculatorViewModel.append(button)
                                        "⌫" -> calculatorViewModel.delete()
                                        else -> calculatorViewModel.append(button)
                                    }
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}
