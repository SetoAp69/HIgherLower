package com.excal.higherlower.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.excal.higherlower.component.CompareMovie
import com.excal.higherlower.data.Movie
import com.excal.higherlower.data.MovieRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class BlitzViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    //To determine the UI state, wether is it ready or not to start the game
    private val _movieListFlow = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val movieListFlow = _movieListFlow

    private lateinit var currentLastMovie: Movie

    //To determine in gamestate
    private val _gameState = MutableStateFlow(BlitzGameState())
    val gameState: StateFlow<BlitzGameState> = _gameState

    private var currentScore = 0
    private var currentStreak = 0

    private var timerJob: Job? = null

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

                //Changing UI State
                _movieListFlow.value = UiState.Success(call)

                Log.d(TAG, "Successfully fetch the data")

            } catch (e: Exception) {
                Log.i(TAG, "Error Getting Movie Data")
            }
        }
    }

    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_gameState.value.timer > 0) {
                delay(1000)
                _gameState.update {
                    it.copy(
                        timer = it.timer - 1
                    )
                }
            }
        }
    }

    fun nextMovie() {
        viewModelScope.launch {
            Log.d(TAG, "nextMovie() called")
            if (_gameState.value.timer == 0 && !_gameState.value.isAnswered) {
                currentStreak = 0
                _gameState.update {
                    it.copy(
                        movieIndex = it.movieIndex + 1,
                        streak = 0,
                        timer = 10
                    )
                }
            }
        }
    }

    fun setImageReady() {
        _gameState.update {
            it.copy(
                isImageReady = true
            )
        }
    }

    fun setImageNotReady() {
        _gameState.update {
            it.copy(
                isImageReady = false
            )
        }
    }


    fun onCompareClick(movieIndex: Int, list: List<Movie>, isHigher: Boolean) {
        viewModelScope.launch {
            val currentState = _gameState.value
            val listSize = list.size
            if (currentState.timer == 0) {
                if (_gameState.value.movieIndex != listSize - 2) {
                    _gameState.update {
                        it.copy(
                            movieIndex = it.movieIndex + 1
                        )
                    }
                } else {
                    _gameState.update {
                        it.copy(
                            isFinish = true
                        )
                    }
                }

                currentStreak = 0
            } else {
                if (!_gameState.value.isAnswered) {
                    if (isHigher) {
                        if (CompareMovie(list[movieIndex + 1], list[movieIndex])) {
                            _gameState.update {
                                it.copy(
                                    isCorrect = true,
                                )
                            }
                            currentStreak++
                            currentScore =
                                (currentScore+50*((10-_gameState.value.timer)/10)+50*(0.1*currentStreak))
                                    .toInt()

                        } else {
                            _gameState.update {
                                it.copy(
                                    isCorrect = false,
                                )
                            }
                            currentStreak = 0
                        }
                    } else {
                        if (CompareMovie(list[movieIndex], list[movieIndex + 1])) {
                            _gameState.update {
                                it.copy(
                                    isCorrect = true,
                                )
                            }
                            currentStreak++
                            currentScore =
                                (currentScore + 50 * ((10 - _gameState.value.timer) / 10) + 50 * (0.1 * currentStreak))
                                    .toInt()


                        } else {
                            _gameState.update {
                                it.copy(
                                    isCorrect = false,
                                )
                            }
                            currentStreak = 0
                        }
                    }
                    _gameState.update {
                        it.copy(
                            streak = currentStreak,
                            score = currentScore,
                            isAnswered = true
                        )
                    }

                    delay(1000L)
                    if (!_gameState.value.isFinish) {
                        _gameState.update {
                            it.copy(
                                isAnswered = false,
                                isCorrect = false,
                                movieIndex = if (movieIndex == listSize - 2) movieIndex else movieIndex + 1,
                                isFinish = movieIndex == listSize - 2,
                                timer = 10


                            )
                        }

                    }

                }
            }
        }
    }


}