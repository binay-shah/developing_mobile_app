package com.example.popularmovies.ui.list.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.popularmovies.data.MovieRepository
import com.example.popularmovies.storage.SharedPrefsManager

class DiscoverMoviesViewModelFactory(

    private val movieRepository: MovieRepository, private val sharedPrefsManager: SharedPrefsManager
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiscoverMoviesViewModel::class.java)) {
            return DiscoverMoviesViewModel(movieRepository, sharedPrefsManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}