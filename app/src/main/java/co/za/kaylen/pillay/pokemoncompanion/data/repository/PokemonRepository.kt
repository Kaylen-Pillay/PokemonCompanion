package co.za.kaylen.pillay.pokemoncompanion.data.repository

import co.za.kaylen.pillay.pokemoncompanion.data.model.response.PokemonListResponse

interface PokemonRepository {

    suspend fun getPokemon(numberOfPokemon: Int): Result<PokemonListResponse>

}