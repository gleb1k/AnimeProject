package com.animeproject.ui.screen.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.animeproject.R
import com.animeproject.ui.theme.custom.CustomTheme

@Composable
fun BaseScreen(
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(CustomTheme.colors.primaryBackground),
    ) {
        Text(
            stringResource(R.string.base),
            color = CustomTheme.colors.tintColor,
            style = CustomTheme.typography.heading
        )
    }
}
