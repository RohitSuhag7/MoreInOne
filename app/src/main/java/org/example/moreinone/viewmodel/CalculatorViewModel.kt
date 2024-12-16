package org.example.moreinone.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import net.objecthunter.exp4j.ExpressionBuilder
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor() : ViewModel() {
    val expression = mutableStateOf("")

    fun clear() {
        expression.value = ""
    }

    fun append(char: String) {
        if (char in "0123456789") {
            expression.value += char
        } else if (char in "+-×÷") {
            if (expression.value.isNotEmpty()) {
                val lastChar = expression.value.last()

                if (lastChar in "+-×÷") {
                    expression.value = expression.value.dropLast(1)
                }
            }
            expression.value += char
        } else if (char in ".") {
            if (expression.value.isNotEmpty()) {
                val lastChar = expression.value.last()

                if (lastChar != '.') {
                    if (lastChar in "+-×÷") {
                        expression.value += "0"
                    }
                    expression.value += char
                }
            }
        } else if (char in "(") {
            if (expression.value.isNotEmpty()) {
                val lastChar = expression.value.last()

                if (lastChar !in "+-×÷") {
                    expression.value += "×"
                }
            }
            expression.value += char
        } else if (char in ")") {
            expression.value += char
        }
    }

    fun delete() {
        if (expression.value.isNotEmpty()) {
            expression.value = expression.value.dropLast(1)
        }
    }

    fun evaluateExpression(): Double {
        return try {
            expression.value = expression.value.replace("×", "*").replace("÷", "/")

            // Using "objecthunter:exp4j" Library
            val result = ExpressionBuilder(expression.value).build().evaluate()

            expression.value = if (result % 1 == 0.0) {
                result.toInt().toString()
            } else {
                String.format("%.${3}f", result)
            }
            result
        } catch (e: Exception) {
            throw Exception("Error")
        }
    }
}
