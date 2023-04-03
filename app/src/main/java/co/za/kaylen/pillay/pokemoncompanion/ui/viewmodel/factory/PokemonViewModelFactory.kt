package co.za.kaylen.pillay.pokemoncompanion.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.za.kaylen.pillay.pokemoncompanion.data.network.PokemonCompanionNetwork
import co.za.kaylen.pillay.pokemoncompanion.data.repository.impl.PokemonRepositoryImpl
import co.za.kaylen.pillay.pokemoncompanion.ui.viewmodel.PokemonListViewModel
import kotlinx.coroutines.Dispatchers

class PokemonViewModelFactory : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonListViewModel::class.java)) {
            return PokemonListViewModel(
                repository = PokemonRepositoryImpl(
                    adapter = PokemonCompanionNetwork.getAdapter(),
                    dispatcher = Dispatchers.IO
                )
            ) as T
        }

        throw IllegalStateException("Unable to create PokemonListViewModel")
    }

}