package co.za.kaylen.pillay.pokemoncompanion.data.network

import co.za.kaylen.pillay.pokemoncompanion.data.model.response.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonNetworkAdapter {

    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int): PokemonListResponse

}