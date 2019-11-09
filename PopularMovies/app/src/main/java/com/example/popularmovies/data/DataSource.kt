package com.example.popularmovies.data

import androidx.lifecycle.LiveData
import com.example.popularmovies.data.model.MovieDetails
import com.example.popularmovies.data.model.MovieProperty
import com.example.popularmovies.data.model.MovieResponse
import com.example.popularmovies.data.remote.network.MovieApiFilter
import com.example.popularmovies.data.remote.network.Resource
import com.example.popularmoviesapp.data.model.MoviesNetworkResult



interface DataSource{
    suspend fun loadMoviesFilteredBy(movieFilter: MovieApiFilter, page: Int): MovieResponse

     fun loadMovie(movieId: Long): LiveData<Resource<MovieDetails>>
}