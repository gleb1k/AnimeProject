package com.animeproject.ui.theme.custom

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

@Composable
fun CustomTheme(
    style: CustomStyle = CustomStyle.Purple,
    textSize: CustomSize = CustomSize.Medium,
    paddingSize: CustomSize = CustomSize.Medium,
    corners: CustomCorners = CustomCorners.Rounded,
    darkTheme: Boolean = isSystemInDarkTheme(),
    fontFamilies: FontFamilies = FontFamilies.Normal,
    content: @Composable () -> Unit
) {
    val colors = when {
        darkTheme -> {
            when (style) {
                CustomStyle.Purple -> purpleDarkPalette
                CustomStyle.Blue -> blueDarkPalette
                CustomStyle.Orange -> orangeDarkPalette
                CustomStyle.Red -> redDarkPalette
                CustomStyle.Green -> greenDarkPalette
            }
        }

        else -> {
            when (style) {
                CustomStyle.Purple -> purpleLightPalette
                CustomStyle.Blue -> blueLightPalette
                CustomStyle.Orange -> orangeLightPalette
                CustomStyle.Red -> redLightPalette
                CustomStyle.Green -> greenLightPalette
            }
        }
    }

    val typography = CustomTypography(
        heading = TextStyle(
            fontFamily = when (fontFamilies) {
                FontFamilies.Normal -> FontFamily.SansSerif
                FontFamilies.Cursive -> FontFamily.Cursive
            },
            fontSize = when (textSize) {
                CustomSize.Small -> 16.sp
                CustomSize.Medium -> 18.sp
                CustomSize.Big -> 20.sp
            },
            fontWeight = FontWeight.Bold
        ),
        large = TextStyle(
            fontFamily = when (fontFamilies) {
                FontFamilies.Normal -> FontFamily.Monospace
                FontFamilies.Cursive -> FontFamily.Cursive
            },
            fontSize = when (textSize) {
                CustomSize.Small -> 20.sp
                CustomSize.Medium -> 22.sp
                CustomSize.Big -> 24.sp
            },
            fontWeight = FontWeight.Bold
        ),
        body = TextStyle(
            fontFamily = when (fontFamilies) {
                FontFamilies.Normal -> FontFamily.SansSerif
                FontFamilies.Cursive -> FontFamily.Cursive
            },
            fontSize = when (textSize) {
                CustomSize.Small -> 14.sp
                CustomSize.Medium -> 16.sp
                CustomSize.Big -> 18.sp
            },
            fontWeight = FontWeight.Normal
        ),
        toolbar = TextStyle(
            fontFamily = when (fontFamilies) {
                FontFamilies.Normal -> FontFamily.SansSerif
                FontFamilies.Cursive -> FontFamily.Cursive
            },
            fontSize = when (textSize) {
                CustomSize.Small -> 14.sp
                CustomSize.Medium -> 16.sp
                CustomSize.Big -> 18.sp
            },
            fontWeight = FontWeight.Medium
        ),
        caption = TextStyle(
            fontFamily = when (fontFamilies) {
                FontFamilies.Normal -> FontFamily.SansSerif
                FontFamilies.Cursive -> FontFamily.Cursive
            },
            fontSize = when (textSize) {
                CustomSize.Small -> 10.sp
                CustomSize.Medium -> 12.sp
                CustomSize.Big -> 14.sp
            }
        )
    )


    val shapes = CustomShape(
        padding = when (paddingSize) {
            CustomSize.Small -> 12.dp
            CustomSize.Medium -> 16.dp
            CustomSize.Big -> 20.dp
        },
        cornersStyle = when (corners) {
            CustomCorners.Flat -> RoundedCornerShape(0.dp)
            CustomCorners.Rounded -> RoundedCornerShape(8.dp)
        }
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.primaryBackground.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    CompositionLocalProvider(
        LocalCustomColors provides colors,
        LocalCustomTypography provides typography,
        LocalCustomShape provides shapes,
        content = content
    )
}
