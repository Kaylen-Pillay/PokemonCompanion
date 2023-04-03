package co.za.kaylen.pillay.pokemoncompanion.data.model.response

import co.za.kaylen.pillay.pokemoncompanion.data.model.Pokemon
import com.google.gson.annotations.SerializedName

data class PokemonListResponse(
    @SerializedName("results") val items: List<Pokemon>
)