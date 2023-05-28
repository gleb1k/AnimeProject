package com.animeproject.ui.theme.custom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class CustomColors(
    val primaryText: Color,
    val primaryBackground: Color,
    val secondaryText: Color,
    val secondaryBackground: Color,
    val tintColor: Color,
    val controlColor: Color,
    val errorColor: Color,
)

data class CustomTypography(
    val heading: TextStyle,
    val body: TextStyle,
    val toolbar: TextStyle,
    val caption: TextStyle
)

data class CustomShape(
    val padding: Dp,
    val cornersStyle: Shape
)

data class CustomImages(
    val id: Int,
    val contentDesc: String
)

object CustomTheme {
    val colors: CustomColors
        @Composable
        get() = LocalCustomColors.current

    val typography: CustomTypography
        @Composable
        get() = LocalCustomTypography.current

    val shapes: CustomShape
        @Composable
        get() = LocalCustomShape.current
}

enum class CustomStyle {
    Purple, Orange, Blue, Red, Green
}

enum class CustomSize {
    Small, Medium, Big
}

enum class CustomCorners {
    Flat, Rounded
}

val LocalCustomColors = staticCompositionLocalOf<CustomColors> {
    error("No colors provided")
}

val LocalCustomTypography = staticCompositionLocalOf<CustomTypography> {
    error("No font provided")
}

val LocalCustomShape = staticCompositionLocalOf<CustomShape> {
    error("No shapes provided")
}
