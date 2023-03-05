package com.mobiledev.gradecalculator.composables

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit

@Composable
fun Button(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit = TextUnit.Unspecified,
    onClick: () -> Unit
) {
    androidx.compose.material.Button(
        modifier = modifier,
        enabled = enabled,
        onClick = { onClick() }
    ) {
        Text(
            text = text,
            modifier = Modifier.align(Alignment.CenterVertically),
            fontSize = fontSize,
            fontWeight = fontWeight,
            textAlign = TextAlign.Center
        )
    }
}