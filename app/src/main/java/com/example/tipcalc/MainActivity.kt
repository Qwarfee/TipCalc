package com.example.tipcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TipCalcScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    width: Dp,
    keyboardType: KeyboardType
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(fontSize = 14.sp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .width(width)
            .height(24.dp)
            .background(Color(0xFFFFADC9)),
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                innerTextField()
            }
        }
    )
}

@Composable
fun TipCalcScreen(modifier: Modifier = Modifier) {
    var amount by remember { mutableStateOf("") }
    var dishes by remember { mutableStateOf("") }
    var tip by remember { mutableFloatStateOf(0f) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Сумма заказа
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 12.dp)
        ) {
            Text(text = "Сумма заказа:", fontSize = 12.sp)
            Spacer(modifier = Modifier.width(8.dp))
            InputField(
                value = amount,
                onValueChange = { new ->
                    val fixed = new.replace(',', '.')
                    if (fixed.all { it.isDigit() || it == '.' } && fixed.count { it == '.' } <= 1) {
                        amount = fixed
                    }
                },
                width = 130.dp,
                keyboardType = KeyboardType.Decimal
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Количество блюд
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 18.dp)
        ) {
            Text(text = "Количество блюд:", fontSize = 12.sp)
            Spacer(modifier = Modifier.width(4.dp))
            InputField(
                value = dishes,
                onValueChange = { new ->
                    if (new.all { it.isDigit() }) dishes = new
                },
                width = 48.dp,
                keyboardType = KeyboardType.Number
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        // Чаевые: слайдер от 0 до 25
        Text(text = "Чаевые:", fontSize = 12.sp)
        Slider(
            value = tip,
            onValueChange = { tip = it },
            valueRange = 0f..25f
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "0", fontSize = 20.sp)
            Text(text = "25", fontSize = 20.sp)
        }
    }
}