package com.animeproject.utils

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.animeproject.R

sealed class Screen(
    val route: String,
    @StringRes
    val name: Int? = null,
    val icon: ImageVector? = null,
) {
    object Settings : Screen(
        route = "settings",
        name = R.string.settings,
        icon = Icons.Filled.Settings,
    )

    object Search : Screen(
        route = "search",
        name = R.string.search,
        icon = Icons.Filled.Search
    )

    object Base : Screen(
        route = "base",
        name = R.string.base,
        icon = Icons.Filled.Person
    )

    object Anime : Screen("anime/{animeId}")
    object Character : Screen("character/{characterId}")

}
