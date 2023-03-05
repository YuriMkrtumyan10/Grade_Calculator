package com.mobiledev.gradecalculator.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mobiledev.gradecalculator.ui.theme.Green
import com.mobiledev.gradecalculator.ui.theme.Purple

@Composable
fun TextField(
    input: String,
    label: String,
    isValid: Boolean,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    errorMessage: String = "Grade should be between 0-100"
) {
    var message by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(60.dp)
                .then(modifier)
            ,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                autoCorrect = true
            ),
            label = { Text(text = label, color = Purple, fontWeight = FontWeight.Bold)},
            colors = TextFieldDefaults.textFieldColors(
                textColor = if (isValid) Color.Black else Color.Red,
                errorLabelColor = Color.Red,
                backgroundColor = Color.LightGray,
                focusedIndicatorColor = if (isValid) Green else Color.Red,
                unfocusedIndicatorColor = if (isValid) Color.Gray else Color.Red,
                disabledIndicatorColor = Color.LightGray
            ),
            value = input,
            onValueChange = { change -> onValueChange(change) }
        )

        message = when (isValid) {
            true -> ""
            false -> errorMessage
        }

        Text(
            text = message,
            color = Color.Red,
            style = MaterialTheme.typography.caption
        )

        Spacer(modifier = Modifier.height(10.dp))
    }
}