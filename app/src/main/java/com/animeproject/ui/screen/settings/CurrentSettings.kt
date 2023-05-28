package com.animeproject.ui.screen.settings

import com.animeproject.ui.theme.custom.CustomCorners
import com.animeproject.ui.theme.custom.CustomSize
import com.animeproject.ui.theme.custom.CustomStyle
import com.animeproject.ui.theme.custom.FontFamilies

data class CurrentSettings(
    val isDarkMode: Boolean,
    val textSize: CustomSize,
    val paddingSize: CustomSize,
    val cornerStyle: CustomCorners,
    val style: CustomStyle,
    val fontFamily: FontFamilies
)
