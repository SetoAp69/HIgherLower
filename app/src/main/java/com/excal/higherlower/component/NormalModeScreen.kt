package com.excal.higherlower.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.excal.higherlower.data.MovieApi
import com.excal.higherlower.ui.MovieViewModel
import com.excal.higherlower.ui.MovieViewModelFactory
import com.excal.higherlower.ui.UiState
import kotlinx.coroutines.delay

@Composable
fun NormalModeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
    ) {
    val factory = MovieViewModelFactory(MovieApi.retrofitServices)
    val viewModel: MovieViewModel = viewModel(factory = factory)
    var movieIndex by remember { mutableStateOf(0) }

    val movieList = viewModel.movieListFlow.collectAsState(initial = UiState.Loading)
    when (val state = movieList.value) {
        is UiState.Loading -> {
            val alpha = remember { androidx.compose.animation.core.Animatable(1f) }
            LaunchedEffect(UiState.Loading) {
                while (true) {
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
                LoadingScreen(alpha = alpha.value)

            }
        }

        is UiState.Success -> {
            val listMovie = state.data
            CompareScreen(listMovie = listMovie, sharedViewModel = viewModel, navController = navController)
        }

        is UiState.Error -> {
            Text(text = "Error")
        }
    }
}

