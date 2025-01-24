package com.excal.higherlower.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.excal.higherlower.presentation.gamedata.DatabaseClient
import com.excal.higherlower.presentation.gamedata.GameData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameOverViewModel() : ViewModel() {


    private val databaseClient = DatabaseClient()
    private val _gameData = mutableStateOf(GameData())
    val gameData: State<GameData> get()= _gameData

    private val _isLoading = mutableStateOf(true)
    val isLoading:State<Boolean> =_isLoading

    init{
        getGameData()
    }
    fun getGameData() {
        viewModelScope.launch {
            _isLoading.value=true
            databaseClient.GetGamedata { gameData ->
                if (gameData != null) {
                    _gameData.value = gameData
                }
                _isLoading.value=false
            }
        }

    }

    fun updateScore(score: Int, gameMode: String) {
        if(isLoading.value){
            return
        }
        viewModelScope.launch {

            if (gameMode == "blitz") {
                if (score > gameData.value.blitz) {
                    databaseClient.updateScore(score = score, gameMode = gameMode)
                }
            } else if (gameMode == "normal") {
//                Log.d(TAG,"${gameData.value}")
                if (score > gameData.value.normal) {
                    databaseClient.updateScore(score = score, gameMode = gameMode)
                }
            }
        }

    }


}