package com.excal.higherlower.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MovieRepository(private val api:APIServices) {

    private val _playedMovieList= MutableLiveData<List<Movie>>()
    val playedMovieList: LiveData<List<Movie>> get()=_playedMovieList

    suspend fun getMovies(authToken:String,page:Int):List<Movie>{
        return try{
                val response=api.getRandomMovies(authToken = authToken,page=page)
                response.results
            }catch (e:Exception){
                emptyList()
            }

    }


}