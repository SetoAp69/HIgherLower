package com.excal.higherlower.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.excal.higherlower.data.APIServices
import com.excal.higherlower.data.MovieRepository

class CompareViewModelFactory(private val sharedViewModel:MovieViewModel): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CompareViewModel::class.java)){
            return CompareViewModel(sharedViewModel) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}