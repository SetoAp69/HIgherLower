package com.excal.higherlower.ui

import androidx.lifecycle.ViewModel
import com.excal.higherlower.data.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CompareViewModel2 : ViewModel(){
    sealed class GameUiState<out T> {
        object Loading : GameUiState<Nothing>()
        data class Playing<T>(val data:T) : GameUiState<T>()
        object Next:GameUiState<Nothing>()
        object Finish:GameUiState<Nothing>()
    }
    private val _gameStateFlow= MutableStateFlow<GameUiState<GameState>>(GameUiState.Playing(
        GameState()
    ))
    val gameStateFlow: StateFlow<GameUiState<GameState>> get()= _gameStateFlow

    data class GameState(
        val isAnswered: Boolean = false,
        val isCorrect: Boolean = false,
        val score: Int = 0,
        val movieIndex: Int = 0,
        val isFinish: Boolean = false,
        val page:Int=0,
    )


}