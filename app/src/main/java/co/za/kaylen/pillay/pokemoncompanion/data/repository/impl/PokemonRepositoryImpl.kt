package co.za.kaylen.pillay.pokemoncompanion.data.repository.impl

import android.net.Uri
import androidx.compose.ui.text.capitalize
import co.za.kaylen.pillay.pokemoncompanion.data.model.response.PokemonListResponse
import co.za.kaylen.pillay.pokemoncompanion.data.network.PokemonNetworkAdapter
import co.za.kaylen.pillay.pokemoncompanion.data.repository.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.*

private const val POKEMON_ICON_URL_PREFIX =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon"

class PokemonRepositoryImpl(
    private val adapter: PokemonNetworkAdapter,
    private val dispatcher: CoroutineDispatcher
) : PokemonRepository {

    override suspend fun getPokemon(numberOfPokemon: Int): Result<PokemonListResponse> =
        withContext(dispatcher) {
            runCatching {
                val response = adapter.getPokemonList(limit = numberOfPokemon)

                /**
                 * Here we are updating the pokemon list to include a URL for each pokemon image.
                 * The API does not send us back the URL by default. We only get this data when we query
                 * the details of a specific pokemon.
                 *
                 * The URL construction for a pokemon image is a static prefix, with a dynamic id suffix
                 * i.e {STATIC_PREFIX}/{DYNAMIC_POKEMON_ID}.png
                 */
                response.copy(
                    items = response.items.map { pokemon ->
                        /**
                         * The pokemon id is also not returned directly when making the API call.
                         * Instead, the URL returned contains the ID for the specific pokemon.
                         *
                         * Here we are pulling out the Pokemon ID from the provided URL, which is
                         * the last path segment value.
                         * i.e. https://pokeapi.co/api/v2/pokemon/{POKEMON_ID}/
                         */
                        val pokemonId = Uri.parse(pokemon.url).pathSegments.lastOrNull() ?: ""

                        pokemon.copy(
                            id = pokemonId,
                            name = pokemon.name.replaceFirstChar { char ->
                                if (char.isLowerCase()) char.titlecase(Locale.ROOT) else char.toString()
                            },
                            icon = "$POKEMON_ICON_URL_PREFIX/${pokemonId}.png"
                        )
                    }
                )
            }
        }

}