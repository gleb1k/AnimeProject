package com.animeproject.ui.screen.settings

import androidx.compose.runtime.staticCompositionLocalOf
import com.animeproject.ui.theme.custom.CustomCorners
import com.animeproject.ui.theme.custom.CustomSize
import com.animeproject.ui.theme.custom.CustomStyle
import com.animeproject.ui.theme.custom.FontFamilies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsEventBus {

    private val _currentSettings: MutableStateFlow<CurrentSettings> = MutableStateFlow(
        CurrentSettings(
            isDarkMode = true,
            cornerStyle = CustomCorners.Rounded,
            style = CustomStyle.Orange,
            textSize = CustomSize.Medium,
            paddingSize = CustomSize.Medium,
            fontFamily = FontFamilies.Normal
        )
    )
    val currentSettings: StateFlow<CurrentSettings> = _currentSettings

    fun updateDarkMode(isDarkMode: Boolean) {
        _currentSettings.value = _currentSettings.value.copy(isDarkMode = isDarkMode)
    }

    fun updateStyle(style: CustomStyle) {
        _currentSettings.value = _currentSettings.value.copy(style = style)
    }

    fun updateFontFamily(fontFamily: FontFamilies) {
        _currentSettings.value = _currentSettings.value.copy(fontFamily = fontFamily)
    }
}

val LocalSettingsEventBus = staticCompositionLocalOf {
    SettingsEventBus()
}
