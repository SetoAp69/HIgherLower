package com.excal.higherlower.ui
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.excal.higherlower.R
import com.excal.higherlower.data.Movie
import com.excal.higherlower.data.MovieApi
import com.excal.higherlower.data.MovieRepository
import com.excal.higherlower.data.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import java.io.IOException

sealed interface MovieUiState{
    data class Success(val data:String):MovieUiState
    object Error:MovieUiState
    object Loading:MovieUiState
}

class PlayViewModel(private val movieRepository: MovieRepository): ViewModel() {

    private val _movieList= MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> get()=_movieList

     fun getMoviesData(){
         val authKey="Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3MWM2ODZiZWFiOTlkYTRmZmZlYzM0OGU5Yzc4NTg5NSIsIm5iZiI6MTczNjAzMzM3Ny43NjE5OTk4LCJzdWIiOiI2Nzc5YzQ2MTJiMDk3YjE1YTI3NGNiNDIiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.nWUbIv3UjN1PEkjNtLE_KPSjBS52JMQntn4ej5yEVv8"

        viewModelScope.launch(Dispatchers.IO){

            try{
                val call=movieRepository.getMovies(authKey)
                withContext(Dispatchers.Main){
                    _movieList.value=call
                }
            }catch(e:Exception){
                Log.i(TAG,"Error Getting Movie Data")
            }

        }

     }



}