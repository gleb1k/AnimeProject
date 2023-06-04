package com.animeproject.ui.screen.util

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import com.animeproject.ui.theme.custom.CustomTheme


@Composable
fun LoadingCompose(
    viewState: ViewState
) {
    if (viewState.loading)
        CircularProgressIndicator(
            color = CustomTheme.colors.errorColor,
        )
}
