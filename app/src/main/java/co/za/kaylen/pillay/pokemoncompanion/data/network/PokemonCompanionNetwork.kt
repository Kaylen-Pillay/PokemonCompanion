package co.za.kaylen.pillay.pokemoncompanion.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PokemonCompanionNetwork {

    private const val POKEMON_API = "https://pokeapi.co/api/v2/"

    private val client: Retrofit = Retrofit.Builder()
        .baseUrl(POKEMON_API)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getAdapter(): PokemonNetworkAdapter = client.create(PokemonNetworkAdapter::class.java)

}