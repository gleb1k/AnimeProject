package com.animeproject.ui.screen.util

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.animeproject.ui.theme.custom.CustomTheme

@Composable
fun ErrorCompose(
    viewState: ViewState
) {
    if (viewState.error != null) {
        Text(
            text = viewState.error!!,
            color = CustomTheme.colors.errorColor,
            style = CustomTheme.typography.large,
        )

    }
}
