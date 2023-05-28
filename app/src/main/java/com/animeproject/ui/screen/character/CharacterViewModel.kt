package com.animeproject.ui.screen.character

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animeproject.data.remote.response.character.CharacterData
import com.animeproject.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CharacterViewState(
    val character: CharacterData? = null,
)

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<CharacterViewState>(CharacterViewState().copy())
    val state: StateFlow<CharacterViewState>
        get() = _state.asStateFlow()

    fun load(id: Int?) {
        viewModelScope.launch {
            id?.let {
                _state.emit(
                    _state.value.copy(
                        character = characterRepository.getCharacterById(id),
                    )
                )
            }
        }
    }
}
