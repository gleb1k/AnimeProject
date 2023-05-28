package com.animeproject.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animeproject.data.remote.response.anime.AnimeData
import com.animeproject.data.remote.response.character.CharacterData
import com.animeproject.domain.repository.AnimeRepository
import com.animeproject.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@Immutable
data class SearchViewState(
    val query: String = "",
    val characters: PersistentList<CharacterData> = persistentListOf(),
    val animes: PersistentList<AnimeData> = persistentListOf()
)

sealed interface SearchEvent {

    data class onSearchClick(val query: String) : SearchEvent
    data class onAnimeClick(val id: Int) : SearchEvent
    data class onCharacterClick(val id: Int) : SearchEvent
    data class onQueryChange(val query: String) : SearchEvent
}

sealed interface SearchAction {

    data class NavigateToAnime(val animeId: Int) : SearchAction
    data class NavigateToCharacter(val characterId: Int) : SearchAction
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    //todo бюджета на Use Case'ы не хватило
    private val characterRepository: CharacterRepository,
    private val animeRepository: AnimeRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<SearchViewState>(SearchViewState())
    val state: StateFlow<SearchViewState>
        get() = _state.asStateFlow()

    private val _action = MutableSharedFlow<SearchAction?>()
    val action: SharedFlow<SearchAction?>
        get() = _action.asSharedFlow()

    fun event(searchEvent: SearchEvent) {
        when (searchEvent) {
            is SearchEvent.onAnimeClick -> onAnimeClick(searchEvent)
            is SearchEvent.onSearchClick -> onSearchClick(searchEvent)
            is SearchEvent.onCharacterClick -> onCharacterClick(searchEvent)
            is SearchEvent.onQueryChange -> onQueryChange(searchEvent)
        }
    }

    private fun onQueryChange(event: SearchEvent.onQueryChange) {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    query = event.query
                )
            )
        }
    }

    private fun onAnimeClick(event: SearchEvent.onAnimeClick) {
        viewModelScope.launch {
            _action.emit(SearchAction.NavigateToAnime(event.id))
        }
    }

    private fun onCharacterClick(event: SearchEvent.onCharacterClick) {
        viewModelScope.launch {
            _action.emit(SearchAction.NavigateToCharacter(event.id))
        }
    }

    private fun onSearchClick(event: SearchEvent.onSearchClick) {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    animes = animeRepository.searchAnimes(event.query).toPersistentList(),
                    characters = characterRepository.searchCharacters(event.query)
                        .toPersistentList(),
                )
            )
        }
    }

}

