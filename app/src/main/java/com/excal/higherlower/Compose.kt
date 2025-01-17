package com.excal.higherlower

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Animatable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.excal.higherlower.component.CompareScreen
import com.excal.higherlower.component.MainMenu
import com.excal.higherlower.component.MainMenuScreen
import com.excal.higherlower.data.MovieApi
import com.excal.higherlower.ui.MovieViewModel
import com.excal.higherlower.ui.MovieViewModelFactory
import com.excal.higherlower.ui.PlayViewModelFactory
import com.excal.higherlower.ui.UiState
import com.excal.higherlower.ui.theme.HIgherLowerTheme
import kotlinx.coroutines.delay

class Compose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HIgherLowerTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
                Column {
                    MainMenu()
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HIgherLowerTheme {
        Greeting("Android")
    }
}

@Composable
fun App(modifier: Modifier = Modifier) {
    val factory = MovieViewModelFactory(MovieApi.retrofitServices)
    val viewModel: MovieViewModel = viewModel(factory = factory)
    var movieIndex by remember{mutableStateOf(0)}

    val movieList = viewModel.movieListFlow.collectAsState(initial = UiState.Loading)
    when (val state = movieList.value) {
        is UiState.Loading -> {
            val alpha=remember{ androidx.compose.animation.core.Animatable(1f) }
            LaunchedEffect(UiState.Loading) {
                while(true){
                    delay(100L)
                    alpha.animateTo(0f)
                    alpha.animateTo(1f)
                }
            }
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Loading", modifier=modifier.alpha(alpha.value))

            }
        }

        is UiState.Success -> {
            val listMovie = state.data
            CompareScreen(listMovie=listMovie, sharedViewModel = viewModel)
        }

        is UiState.Error -> {
            Text(text = "Error")
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0L)
@Composable
private fun AppPreview() {
    HIgherLowerTheme {
        App()
    }
}