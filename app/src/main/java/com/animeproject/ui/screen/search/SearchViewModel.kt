package com.animeproject.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animeproject.data.remote.response.anime.AnimeData
import com.animeproject.data.remote.response.character.CharacterData
import com.animeproject.domain.repository.AnimeRepository
import com.animeproject.domain.repository.CharacterRepository
import com.animeproject.ui.screen.util.ViewState
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
    val animes: PersistentList<AnimeData> = persistentListOf(),
    override val loading: Boolean = false,
    override val error: String? = null,
) : ViewState

sealed interface SearchEvent {

    data class OnSearchClick(val query: String) : SearchEvent
    data class OnAnimeClick(val id: Int) : SearchEvent
    data class OnCharacterClick(val id: Int) : SearchEvent
    data class OnQueryChange(val query: String) : SearchEvent

    data class OnLoading(val isLoading: Boolean) : SearchEvent
    data class OnError(val errorMessage: String?) : SearchEvent
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
            is SearchEvent.OnAnimeClick -> onAnimeClick(searchEvent)
            is SearchEvent.OnSearchClick -> onSearchClick(searchEvent)
            is SearchEvent.OnCharacterClick -> onCharacterClick(searchEvent)
            is SearchEvent.OnQueryChange -> onQueryChange(searchEvent)

            is SearchEvent.OnError -> onError(searchEvent)
            is SearchEvent.OnLoading -> onLoading(searchEvent)
        }
    }

    private fun onError(event: SearchEvent.OnError) {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    error = event.errorMessage
                )
            )
        }
    }

    private fun onLoading(event: SearchEvent.OnLoading) {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    loading = event.isLoading
                )
            )
        }
    }

    private fun onQueryChange(event: SearchEvent.OnQueryChange) {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    query = event.query
                )
            )
        }
    }

    private fun onAnimeClick(event: SearchEvent.OnAnimeClick) {
        viewModelScope.launch {
            _action.emit(SearchAction.NavigateToAnime(event.id))
        }
    }

    private fun onCharacterClick(event: SearchEvent.OnCharacterClick) {
        viewModelScope.launch {
            _action.emit(SearchAction.NavigateToCharacter(event.id))
        }
    }

    private fun onSearchClick(event: SearchEvent.OnSearchClick) {
        viewModelScope.launch {
            try {
                onLoading(SearchEvent.OnLoading(true))
                _state.emit(
                    _state.value.copy(
                        animes = animeRepository.searchAnimes(event.query).toPersistentList(),
                        characters = characterRepository.searchCharacters(event.query)
                            .toPersistentList(),
                    )
                )
                onError(SearchEvent.OnError(null))
            } catch (throwable: Throwable) {
                onError(SearchEvent.OnError("Loading error :c"))
            } finally {
                onLoading(SearchEvent.OnLoading(false))
            }
        }
    }

}

