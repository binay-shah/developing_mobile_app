package com.example.popularmovies.ui.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.popularmovies.data.MovieRepository
import com.example.popularmovies.data.model.MovieProperty


class DetailViewModelFactory(

    private val movieRepository: MovieRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return MovieDetailViewModel( movieRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}