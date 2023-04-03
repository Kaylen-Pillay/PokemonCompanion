package co.za.kaylen.pillay.pokemoncompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.za.kaylen.pillay.pokemoncompanion.data.model.Pokemon
import co.za.kaylen.pillay.pokemoncompanion.ui.state.PokemonListUIState
import co.za.kaylen.pillay.pokemoncompanion.ui.theme.PokemonCompanionTheme
import co.za.kaylen.pillay.pokemoncompanion.ui.viewmodel.PokemonListViewModel
import co.za.kaylen.pillay.pokemoncompanion.ui.viewmodel.factory.PokemonViewModelFactory
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonCompanionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PokemonScreen(
                        viewModel = viewModel(this, factory = PokemonViewModelFactory())
                    )
                }
            }
        }
    }
}

@Composable
private fun PokemonScreen(viewModel: PokemonListViewModel) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Grad Program 2023 - Android Demo"
                )
            }
        }
    ) { contentPadding ->
        when {
            state.isInLoadingState -> LoadingState(contentPadding = contentPadding)
            state.hasPokemonToDisplay -> PokemonList(
                pokemonItems = state.pokemon,
                contentPadding = contentPadding
            )
            else -> EmptyState(contentPadding = contentPadding)
        }
    }

}

@Composable
private fun PokemonList(pokemonItems: List<Pokemon>, contentPadding: PaddingValues) {
    LazyColumn(modifier = Modifier.padding(contentPadding)) {
        items(count = pokemonItems.count()) { index ->
            Pokemon(
                item = pokemonItems[index],
                isFirstItem = index == 0,
                isLastItem = index == pokemonItems.lastIndex
            )
        }
    }
}

@Composable
private fun Pokemon(item: Pokemon, isFirstItem: Boolean, isLastItem: Boolean) {
    Card(
        modifier = Modifier.padding(
            top = if (isFirstItem) 8.dp else 1.dp,
            bottom = if (isLastItem) 8.dp else 1.dp,
            start = 8.dp,
            end = 8.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                PokemonIcon(pokemon = item)
            }
            Spacer(modifier = Modifier.size(16.dp))
            Column {
                Text(textAlign = TextAlign.Center, text = item.name)
            }
        }
    }
}

@Composable
private fun PokemonIcon(pokemon: Pokemon) {
    SubcomposeAsyncImage(
        modifier = Modifier.size(50.dp),
        model = ImageRequest.Builder(LocalContext.current)
            .data(pokemon.icon)
            .crossfade(true)
            .build(),
        contentDescription = "${pokemon.name}'s picture",
        loading = {
            CircularProgressIndicator()
        }
    )
}

@Composable
private fun EmptyState(contentPadding: PaddingValues) {
    Text(
        modifier = Modifier.padding(contentPadding),
        text = PokemonListUIState.EMPTY_LIST_DISPLAY_TEXT
    )
}

@Composable
private fun LoadingState(contentPadding: PaddingValues) {
    Text(
        modifier = Modifier.padding(contentPadding),
        text = PokemonListUIState.LOADING_DISPLAY_TEXT
    )
}