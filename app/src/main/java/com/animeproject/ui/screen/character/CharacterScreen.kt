package com.animeproject.ui.screen.character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.animeproject.ui.theme.custom.CustomTheme
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@Composable
fun CharacterScreen(
    characterId: Int?,
    viewModel: CharacterViewModel = hiltViewModel()
) {
    viewModel.load(characterId)
    val state by viewModel.state.collectAsStateWithLifecycle()

    Content(viewState = state)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Content(
    viewState: CharacterViewState,
) {
    if (viewState.character != null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(CustomTheme.colors.primaryBackground),
        )
        {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CustomTheme.colors.primaryBackground),
                ) {
                    GlideImage(
                        model = viewState.character.images.jpg?.imageUrl
                            ?: viewState.character.images.webp?.imageUrl,
                        contentDescription = "Image",
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                    )
                    Card(
                        elevation = CardDefaults.cardElevation(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(CustomTheme.colors.secondaryBackground),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 8.dp)
                        ) {
                            Text(
                                text = viewState.character.name,
                                color = CustomTheme.colors.primaryText,
                                style = CustomTheme.typography.heading,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                            )
                            if (viewState.character.nicknames.isNotEmpty()) {
                                Text(
                                    text = viewState.character.nicknames[0] ?: "",
                                    color = CustomTheme.colors.primaryText,
                                    style = CustomTheme.typography.body,
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp)
                                        .padding(bottom = 8.dp)
                                )
                            }
                        }

                    }
                    Card(
                        elevation = CardDefaults.cardElevation(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .background(CustomTheme.colors.secondaryBackground),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 8.dp)
                        ) {
                            Text(
                                text = viewState.character.about,
                                color = CustomTheme.colors.primaryText,
                                style = CustomTheme.typography.body,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                            )
                        }
                    }
                }
            }

        }
    }
}


