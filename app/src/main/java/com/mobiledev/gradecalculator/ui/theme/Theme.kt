package com.mobiledev.gradecalculator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Composable
fun GradeCalculatorTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colors =
            lightColors(primary = Purple,
            primaryVariant = Purple700,
            secondary = Teal200),
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}