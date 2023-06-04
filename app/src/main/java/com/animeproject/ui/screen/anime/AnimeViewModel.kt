package com.animeproject.ui.screen.anime

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animeproject.data.remote.response.anime.AnimeData
import com.animeproject.domain.repository.AnimeRepository
import com.animeproject.ui.screen.util.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@Immutable
data class AnimeViewState(
    val anime: AnimeData? = null,

    override val loading: Boolean = true,
    override val error: String? = null
) : ViewState

sealed interface AnimeEvent {
    data class OnCreate(val id: Int) : AnimeEvent

    //todo вот это как-нибудь красиво оформить и потом на каждом экране переиспользовать
    data class OnLoading(val isLoading: Boolean) : AnimeEvent
    data class OnError(val errorMessage: String?) : AnimeEvent
    //
}

@HiltViewModel
class AnimeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val animeRepository: AnimeRepository,
) : ViewModel() {

    //тоже приходит , классно
    private val animeId: Int = checkNotNull(savedStateHandle["animeId"])

    private val _state = MutableStateFlow<AnimeViewState>(AnimeViewState().copy())
    val state: StateFlow<AnimeViewState>
        get() = _state.asStateFlow()

    fun event(animeEvent: AnimeEvent) {
        when (animeEvent) {
            is AnimeEvent.OnCreate -> onCreate(animeEvent)
            is AnimeEvent.OnError -> onError(animeEvent)
            is AnimeEvent.OnLoading -> onLoading(animeEvent)
        }
    }

    private fun onError(event: AnimeEvent.OnError) {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    error = event.errorMessage
                )
            )
        }
    }

    private fun onLoading(event: AnimeEvent.OnLoading) {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    loading = event.isLoading
                )
            )
        }
    }


    private fun onCreate(event: AnimeEvent.OnCreate) {
        viewModelScope.launch {
            try {
                onLoading(AnimeEvent.OnLoading(true))
                _state.emit(
                    _state.value.copy(
                        anime = animeRepository.getAnimeById(event.id)
                    )
                )
            } catch (throwable: Throwable) {
                onError(AnimeEvent.OnError("Loading error :c"))
            } finally {
                onLoading(AnimeEvent.OnLoading(false))
            }
        }
    }

}
