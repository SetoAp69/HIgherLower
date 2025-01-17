package com.excal.higherlower.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.excal.higherlower.component.CompareMovie
import com.excal.higherlower.data.Movie
import com.excal.higherlower.data.MovieRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    //To determine the UI state, wether is it ready or not to start the game
    private val _movieListFlow = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val movieListFlow = _movieListFlow

    private lateinit var currentLastMovie: Movie

    //To determine in gamestate
    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState

    private var currentScore = 0

    init {
        fetchMovie()
    }

    fun fetchMovie(pageIndex: Int = 0) {
        val authKey =
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3MWM2ODZiZWFiOTlkYTRmZmZlYzM0OGU5Yzc4NTg5NSIsIm5iZiI6MTczNjAzMzM3Ny43NjE5OTk4LCJzdWIiOiI2Nzc5YzQ2MTJiMDk3YjE1YTI3NGNiNDIiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.nWUbIv3UjN1PEkjNtLE_KPSjBS52JMQntn4ej5yEVv8"
        val page = (Math.ceil((Math.random() * 10)) + pageIndex).toInt()
        viewModelScope.launch {
            try {
                var call = movieRepository.getMovies(authKey, page).shuffled()

                val data = if (pageIndex != 0) {
                    call.mapIndexed { index, movie ->
                        if (index == 0) {
                            currentLastMovie
                        } else {
                            movie
                        }
                    }
                } else {
                    call
                }
                //Update new LastMovie on the list
                currentLastMovie=data[data.size-1]
                //Changing UI State
                _movieListFlow.value = UiState.Success(data)

                Log.d(TAG,"Successfully fetch the data")

            } catch (e: Exception) {
                Log.i(TAG, "Error Getting Movie Data")
            }
        }
    }



    fun onCompareClick(movieIndex: Int, list: List<Movie>, isHigher: Boolean) {
        viewModelScope.launch {
            val currentState = _gameState.value
            val listSize = list.size
            if (!currentState.isAnswered) {
                if (!_gameState.value.isOutOfMovie) {
                    if (isHigher) {
                        if(CompareMovie(list[movieIndex + 1], list[movieIndex])){
                            _gameState.update {
                                it.copy(
                                    isCorrect = true,
                                )
                            }
                            currentScore++

                        }else{
                            _gameState.update {
                                it.copy(
                                    isCorrect = false,
                                    isFinish = true,
                                )
                            }
                        }
                    } else if (!isHigher  ) {
                        if(CompareMovie(list[movieIndex], list[movieIndex + 1])){
                            _gameState.update {
                                it.copy(
                                    isCorrect = true,
                                )
                            }
                            currentScore++

                        }else{
                            _gameState.update {
                                it.copy(
                                    isCorrect = false,
                                    isFinish = true,
                                )
                            }
                        }

                    }
                    _gameState.update {
                        it.copy(
                            score = currentScore,
                            isAnswered = true
                        )
                    }
                    delay(1000L)
                    if(_gameState.value.isFinish){
                        Log.d(TAG,"GAME FINISHED ! ")
                    }else{
                        _gameState.update {
                            it.copy(

                                isAnswered = false,
                                isCorrect = false,
                                movieIndex = if (movieIndex == listSize - 2) movieIndex else movieIndex + 1,
                                isOutOfMovie = movieIndex == listSize - 2,
                                page = if(movieIndex==listSize-2) it.page+1 else it.page

                            )
                        }
                    }
                    if(_gameState.value.isOutOfMovie){
                        fetchMovie(pageIndex = _gameState.value.page)
                        _gameState.update {
                            it.copy(
                                movieIndex = 0,
                                isOutOfMovie = false
                            )
                        }
                    }
                }


            }
        }
    }


}