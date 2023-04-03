package co.za.kaylen.pillay.pokemoncompanion.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.za.kaylen.pillay.pokemoncompanion.data.repository.PokemonRepository
import co.za.kaylen.pillay.pokemoncompanion.ui.state.PokemonListUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val NUMBER_OF_POKEMON = 100

class PokemonListViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<PokemonListUIState> = MutableStateFlow(
        PokemonListUIState(isInLoadingState = true)
    )
    val uiState: StateFlow<PokemonListUIState> = _uiState

    init {
        viewModelScope.launch {
            val response = repository.getPokemon(numberOfPokemon = NUMBER_OF_POKEMON)
            _uiState.update { state ->
                state.copy(
                    pokemon = response.getOrNull()?.items ?: emptyList(),
                    isInLoadingState = false
                )
            }
        }
    }

}