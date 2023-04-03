package co.za.kaylen.pillay.pokemoncompanion.ui.state

import co.za.kaylen.pillay.pokemoncompanion.data.model.Pokemon

data class PokemonListUIState(
    val pokemon: List<Pokemon> = emptyList(),
    val isInLoadingState: Boolean = false
) {
    val hasPokemonToDisplay: Boolean = pokemon.isNotEmpty()

    companion object {
        const val EMPTY_LIST_DISPLAY_TEXT = "Oh no \uD83D\uDE14, we are unable to show you any Pokemon."
        const val LOADING_DISPLAY_TEXT = "Getting your Pokemon ready! \uD83C\uDFCB️"
    }
}