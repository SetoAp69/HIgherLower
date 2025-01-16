package com.excal.higherlower.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.excal.higherlower.data.APIServices
import com.excal.higherlower.data.MovieRepository

class MovieViewModelFactory(private val api: APIServices): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieViewModel::class.java)){
            return MovieViewModel(MovieRepository(api)) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}