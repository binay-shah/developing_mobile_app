package com.example.popularmovies.ui.list.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovies.data.MovieRepository
import com.example.popularmovies.data.model.Movie

class FavouritesViewModel(private val mMovieRepository: MovieRepository) : ViewModel(){

    val favouriteList : LiveData<List<Movie>> = mMovieRepository.getAllFavouriteMovies()

    private val _navigateToSelectedMovie = MutableLiveData<Movie>()

    val navigateToSelectedMovie : LiveData<Movie>
        get() = _navigateToSelectedMovie


    fun displayMovieDetails(movie : Movie){
        _navigateToSelectedMovie.value = movie
    }

    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }



}