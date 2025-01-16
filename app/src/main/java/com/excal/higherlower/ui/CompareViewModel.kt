package com.excal.higherlower.ui

import android.app.GameState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.excal.higherlower.component.CompareMovie
import com.excal.higherlower.data.Movie
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CompareViewModel(
    sharedViewModel: MovieViewModel
) : ViewModel() {
    var sharedViewModel=sharedViewModel
    private val _state = MutableStateFlow(GameState())
    var state: StateFlow<com.excal.higherlower.ui.GameState> = _state
    var currentScore = 0

    fun onCompareClick(movieIndex: Int, list: List<Movie>, isHigher: Boolean) {
        viewModelScope.launch {
            val currentState = _state.value
            val listSize = list.size
            if (!currentState.isAnswered) {
                if (!_state.value.isFinish) {
                    if (isHigher && CompareMovie(list[movieIndex + 1], list[movieIndex])) {
                        _state.update {
                            it.copy(
                                isCorrect = true,
                            )
                        }
                        currentScore++
                    } else if (!isHigher && CompareMovie(list[movieIndex], list[movieIndex + 1])) {
                        _state.update {
                            it.copy(
                                isCorrect = true

                            )
                        }
                        currentScore++

                    }
                    _state.update {
                        it.copy(
                            score = currentScore,
                            isAnswered = true
                        )
                    }
                    delay(1000L)
                    _state.update {
                        it.copy(

                            isAnswered = false,
                            isCorrect = false,
                            movieIndex = if (movieIndex == listSize - 2) movieIndex else movieIndex + 1,
                            isFinish = movieIndex == listSize - 2
                        )
                    }
                } else  {

                    sharedViewModel.fetchMovie(page = _state.value.page)
                    _state.update{
                        it.copy(
                            movieIndex = 0,
                            isFinish = false
                        )
                    }

                }


            }
        }
    }
}

data class GameState(
    val isAnswered: Boolean = false,
    val isCorrect: Boolean = false,
    val score: Int = 0,
    val movieIndex: Int = 0,
    val isFinish: Boolean = false,
    val page: Int = 0,
)