package com.excal.higherlower.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.excal.higherlower.data.Movie
import com.excal.higherlower.data.MovieRepository
import com.excal.higherlower.data.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}


class MovieViewModel(private val movieRepository: MovieRepository): ViewModel() {

    private val _movieListFlow = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val movieListFlow=_movieListFlow

    init {
        fetchMovie()
    }



    fun fetchMovie (page:Int=0){
        val authKey="Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3MWM2ODZiZWFiOTlkYTRmZmZlYzM0OGU5Yzc4NTg5NSIsIm5iZiI6MTczNjAzMzM3Ny43NjE5OTk4LCJzdWIiOiI2Nzc5YzQ2MTJiMDk3YjE1YTI3NGNiNDIiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.nWUbIv3UjN1PEkjNtLE_KPSjBS52JMQntn4ej5yEVv8"
        val page=(Math.ceil((Math.random()*10))+page).toInt()
        viewModelScope.launch {
            try{
                val call=movieRepository.getMovies(authKey,page).shuffled()
                _movieListFlow.value=UiState.Success(call)
                movieRepository.updatePlayedMovie(call)
            }catch(e:Exception){
                Log.i(TAG,"Error Getting Movie Data")
            }
        }
    }
    fun fetchMovieIfNeeded(page: Int) {
        if (_movieListFlow.value is UiState.Loading) { // Check if already loading
            fetchMovie(page) // Only fetch if not already loading
        }
    }



}