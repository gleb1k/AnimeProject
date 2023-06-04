@file:OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)

package com.animeproject.ui.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.animeproject.data.remote.response.anime.AnimeData
import com.animeproject.data.remote.response.character.CharacterData
import com.animeproject.ui.theme.custom.CustomTheme
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
private fun SearchScreenActions(
    navController: NavController,
    viewAction: SearchAction?
) {
    LaunchedEffect(viewAction) {
        when (viewAction) {
            is SearchAction.NavigateToAnime -> navController.navigate("anime/${viewAction.animeId}")
            is SearchAction.NavigateToCharacter -> navController.navigate("character/${viewAction.characterId}")
            null -> Unit
        }
    }
}

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    Content(
        viewState = state,
        eventHandler = viewModel::event
    )

    SearchScreenActions(
        navController = navController,
        viewAction = action
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    viewState: SearchViewState,
    eventHandler: (SearchEvent) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .background(CustomTheme.colors.primaryBackground),
    ) {
        item {
            OutlinedTextField(
                value = viewState.query,
                onValueChange = {
                    eventHandler.invoke(SearchEvent.onQueryChange(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 4.dp),
                keyboardOptions = KeyboardOptions().copy(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        eventHandler.invoke(SearchEvent.onSearchClick(viewState.query))
                    }
                ),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = CustomTheme.colors.primaryText,
                    focusedContainerColor = CustomTheme.colors.secondaryBackground,
                    unfocusedContainerColor = CustomTheme.colors.secondaryBackground,
                    disabledContainerColor = CustomTheme.colors.secondaryBackground,
                    focusedIndicatorColor = CustomTheme.colors.tintColor,
                    unfocusedLabelColor = CustomTheme.colors.controlColor,
                    unfocusedTextColor = CustomTheme.colors.controlColor
                ),
                label = {
                    Text("Anime title or character name", style = CustomTheme.typography.caption)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            TextCenter(text = "Animes", isVisible = viewState.animes.isNotEmpty())
        }
        items(viewState.animes, key = { it.malId }) { animeData ->
            AnimeItem(anime = animeData, onClick = {
                eventHandler.invoke(SearchEvent.onAnimeClick(animeData.malId))
            })
        }
        item {
            TextCenter(text = "Characters", isVisible = viewState.characters.isNotEmpty())
        }
        items(viewState.characters, key = { it.malId }) { characterData ->
            CharacterItem(character = characterData, onClick = {
                eventHandler.invoke(SearchEvent.onCharacterClick(characterData.malId))
            })
        }

    }
}

@Composable
private fun TextCenter(
    text: String,
    isVisible: Boolean = true
) {
    if (isVisible) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = text,
                color = CustomTheme.colors.primaryText,
                style = CustomTheme.typography.large,
            )
        }
    }

}

@Composable
private fun AnimeItem(
    anime: AnimeData,
    onClick: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke(anime.malId)
            }
            .padding(horizontal = 8.dp)
            .background(CustomTheme.colors.secondaryBackground),

        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = anime.images.jpg?.imageUrl ?: anime.images.webp?.imageUrl,
            contentDescription = anime.title,
            contentScale = ContentScale.None,
            modifier = Modifier
                .height(150.dp)
                .width(100.dp)
                .padding(vertical =  2.dp)

        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
        ) {
            Text(
                text = anime.title,
                color = CustomTheme.colors.primaryText,
                style = CustomTheme.typography.heading
            )
            if (anime.score != 0.0)
                Text(
                    text = "Score: ${anime.score}", color = CustomTheme.colors.primaryText,
                    style = CustomTheme.typography.body
                )
        }
    }
    ItemDivider()
}

@Composable
private fun ItemDivider() {
    Divider(
        modifier = Modifier.padding(1.dp),
        thickness = 0.5.dp,
        color = CustomTheme.colors.tintColor
    )
}

@Composable
private fun CharacterItem(
    character: CharacterData,
    onClick: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke(character.malId)
            }
            .padding(horizontal = 8.dp, vertical = 1.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = character.images.jpg?.imageUrl ?: character.images.webp?.imageUrl,
            contentDescription = character.name,
            contentScale = ContentScale.None,
            modifier = Modifier
                .height(150.dp)
                .width(100.dp)
                .padding(vertical =  2.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
        ) {
            Text(
                text = character.name,
                color = CustomTheme.colors.primaryText,
                style = CustomTheme.typography.heading
            )
            Text(
                text = character.nameKanji ?: "",
                color = CustomTheme.colors.primaryText,
                style = CustomTheme.typography.body
            )
        }
    }
    ItemDivider()
}

//@Preview(showBackground = true)
//@Composable
//private fun ScreenPreview() {
//    AnimeProjectTheme {
//        Content(viewState = SearchViewState(
//            query = "test",
//            characters = listOf(
//                CharacterData(
//                    malId = 1,
//                    about = "",
//                    favorites = 1,
//                    images = Images(
//                        jpg = Jpg(
//                            imageUrl = "https://cdn.myanimelist.net/images/characters/2/46905.jpg",
//                            smallImageUrl = null
//                        ),
//                        webp = null
//                    ),
//                    name = "test",
//                    nameKanji = "",
//                    url = "",
//                    nicknames = listOf()
//                ),
//                CharacterData(
//                    malId = 1,
//                    about = "",
//                    favorites = 1,
//                    images = Images(
//                        jpg = Jpg(
//                            imageUrl = "https://cdn.myanimelist.net/images/characters/11/294388.jpg",
//                            smallImageUrl = null
//                        ),
//                        webp = null
//                    ),
//                    name = "test",
//                    nameKanji = "",
//                    url = "",
//                    nicknames = listOf()
//                )
//            ),
//            animes = listOf()
//        ), eventHandler = {})
//    }
//}

