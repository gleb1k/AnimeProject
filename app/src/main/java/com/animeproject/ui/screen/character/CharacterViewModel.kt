package com.animeproject.ui.screen.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animeproject.data.remote.response.character.CharacterData
import com.animeproject.domain.repository.CharacterRepository
import com.animeproject.ui.screen.util.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@Immutable
data class CharacterViewState(
    val character: CharacterData? = null,
    override val loading: Boolean = true,
    override val error: String? = null
) : ViewState

sealed interface CharacterEvent {
    data class OnCreate(val id: Int) : CharacterEvent

    //todo вот это тоже как-нибудь красиво оформить и потом на каждом экране переиспользовать
    data class OnLoading(val isLoading: Boolean) : CharacterEvent
    data class OnError(val errorMessage: String?) : CharacterEvent
    //
}

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<CharacterViewState>(CharacterViewState().copy())
    val state: StateFlow<CharacterViewState>
        get() = _state.asStateFlow()

    fun event(characterEvent: CharacterEvent) {
        when (characterEvent) {
            is CharacterEvent.OnCreate -> onCreate(characterEvent)
            is CharacterEvent.OnError -> onError(characterEvent)
            is CharacterEvent.OnLoading -> onLoading(characterEvent)
        }
    }

    private fun onError(event: CharacterEvent.OnError) {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    error = event.errorMessage
                )
            )
        }
    }

    private fun onLoading(event: CharacterEvent.OnLoading) {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    loading = event.isLoading
                )
            )
        }
    }


    private fun onCreate(event: CharacterEvent.OnCreate) {
        viewModelScope.launch {
            try {
                onLoading(CharacterEvent.OnLoading(true))
                _state.emit(
                    _state.value.copy(
                        character = characterRepository.getCharacterById(event.id)
                    )
                )
            } catch (throwable: Throwable) {
                onError(CharacterEvent.OnError("Loading error :c"))
            } finally {
                onLoading(CharacterEvent.OnLoading(false))
            }
        }
    }
}
