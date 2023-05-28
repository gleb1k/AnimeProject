package com.animeproject.ui.screen.anime

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animeproject.data.remote.response.anime.AnimeData
import com.animeproject.domain.repository.AnimeRepository
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
)


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

    fun loadAnime(id: Int?) {
        viewModelScope.launch {
            id?.let {
                _state.emit(
                    _state.value.copy(
                        anime = animeRepository.getAnimeById(id),
                    )
                )
            }
        }
    }
}
