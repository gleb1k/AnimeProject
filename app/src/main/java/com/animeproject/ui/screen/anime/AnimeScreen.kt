@file:OptIn(ExperimentalGlideComposeApi::class)

package com.animeproject.ui.screen.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.animeproject.data.remote.response.anime.Aired
import com.animeproject.data.remote.response.anime.AnimeData
import com.animeproject.data.remote.response.anime.Images
import com.animeproject.data.remote.response.anime.Jpg
import com.animeproject.data.remote.response.anime.Trailer
import com.animeproject.ui.theme.custom.CustomTheme
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@Composable
fun AnimeScreen(
    animeId: Int?,
    viewModel: AnimeViewModel = hiltViewModel()
) {
    viewModel.loadAnime(animeId)
    val state by viewModel.state.collectAsStateWithLifecycle()

    Content(viewState = state)

}

@Composable
fun Content(
    viewState: AnimeViewState,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomTheme.colors.primaryBackground),
    )
    {
        item {
            AnimeItemWithDesc(viewState = viewState)
        }
    }
}

@Composable
fun AnimeItemWithDesc(viewState: AnimeViewState) {
    if (viewState.anime != null) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            GlideImage(
                model = viewState.anime.images.jpg?.largeImageUrl
                    ?: viewState.anime.images.webp?.largeImageUrl,
                contentDescription = "Image",
                contentScale = ContentScale.None,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(320.dp)
                    .width(180.dp)
                    .clip(RoundedCornerShape(2.dp))
            )
            Card(
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),

                ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CustomTheme.colors.secondaryBackground)
                        .padding(vertical = 8.dp)

                ) {
                    Text(
                        text = viewState.anime.titleEnglish ?: "",
                        color = CustomTheme.colors.primaryText,
                        style = CustomTheme.typography.body,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    )
                    Text(
                        text = viewState.anime.title,
                        color = CustomTheme.colors.primaryText,
                        style = CustomTheme.typography.body,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(bottom = 8.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = "${viewState.anime.year} y.",
                            color = CustomTheme.colors.primaryText,
                            style = CustomTheme.typography.body,
                            modifier = Modifier
                                .padding(horizontal = 8.dp),
                        )
                        Text(
                            text = viewState.anime.type ?: "",
                            color = CustomTheme.colors.primaryText,
                            style = CustomTheme.typography.body,
                            modifier = Modifier
                                .padding(horizontal = 8.dp),
                        )
                        Text(
                            text = viewState.anime.score.toString(),
                            color = CustomTheme.colors.primaryText,
                            style = CustomTheme.typography.body,
                            modifier = Modifier
                                .padding(horizontal = 8.dp),
                        )
                    }

                    InfoDivider()
                    InfoText(title = "Rating", text = viewState.anime.rating)
                    InfoDivider()
                    InfoText(title = "Status", text = viewState.anime.status)
                    InfoDivider()
                    InfoText(
                        title = "Episodes",
                        text = viewState.anime.episodes.toString()
                    )
                    InfoDivider()
                    InfoText(title = "Source", text = viewState.anime.source)
                    InfoDivider()
                    InfoText(
                        title = "Aired",
                        text = viewState.anime.aired.string ?: ""
                    )
                    InfoDivider()

                }
            }

        }
        Card(
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(CustomTheme.colors.secondaryBackground)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = viewState.anime.synopsis,
                    color = CustomTheme.colors.primaryText,
                    style = CustomTheme.typography.body,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun InfoText(title: String, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            color = CustomTheme.colors.primaryText,
            style = CustomTheme.typography.body,
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(2f),
        )
        Text(
            text = text,
            color = CustomTheme.colors.primaryText,
            style = CustomTheme.typography.body,
            modifier = Modifier
                .weight(3f),
        )
    }
}

@Composable
private fun InfoDivider() {
    Divider(
        modifier = Modifier.padding(1.dp),
        thickness = 0.5.dp,
        color = CustomTheme.colors.tintColor
    )
}

@Preview
@Composable
private fun AnimePreview() {
    Content(
        viewState = AnimeViewState(
            AnimeData(
                malId = 1,
                url = "https://example.com/anime",
                images = Images(
                    Jpg(
                        "https://cdn.myanimelist.net/images/anime/1522/128039.jpg",
                        null,
                        null
                    ),
                    null
                ),
                trailer = Trailer(
                    null,
                    null,
                    null
                ),
                approved = true,
                title = "My Anime",
                titleEnglish = "My Anime (English)",
                titleJapanese = "私のアニメ",
                titleSynonyms = listOf("Anime A", "Anime B"),
                type = "TV",
                source = "Original",
                episodes = 12,
                status = "Finished Airing",
                airing = false,
                aired = Aired(
                    null,
                    null,
                    null,
                    "sad",
                ),
                duration = "24 min per episode",
                rating = "PG-13",
                score = 8.5,
                scoredBy = 1000,
                rank = 50,
                popularity = 500,
                members = 10000,
                favorites = 2000,
                synopsis = "",
                background = "Background information about the anime",
                season = "Spring 2023",
                year = 2023,
                broadcast = null,
                producers = null,
                licensors = null,
                studios = null,
                genres = null,
                explicitGenres = null,
                themes = null,
                demographics = null,
            )
        )
    )
}
