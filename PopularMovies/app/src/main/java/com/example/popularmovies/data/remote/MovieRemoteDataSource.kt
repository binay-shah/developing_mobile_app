package com.example.popularmovies.data.remote

import android.util.Log
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.popularmovies.data.model.MovieProperty
import com.example.popularmovies.data.model.MovieResponse
import com.example.popularmovies.data.remote.network.*
import com.example.popularmoviesapp.data.model.MoviesNetworkResult
import com.example.popularmoviesapp.data.paging.MovieDataSourceFactory
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import timber.log.Timber


class MovieRemoteDataSource private constructor(private val movieService: MovieService) {

    companion object {
        @Volatile
        private var instance: MovieRemoteDataSource? = null

        fun getInstance(movieService: MovieService) =
            instance ?: synchronized(this) {
                instance ?: MovieRemoteDataSource(movieService).also { instance = it }
            }
    }


    suspend fun loadMoviesFilteredBy(movieFilter: MovieApiFilter, page: Int): MovieResponse {

        var movieResponse: MovieResponse
        if (movieFilter == MovieApiFilter.POPULAR)
            movieResponse = movieService.getPopularMovies(page)
        else
            movieResponse = movieService.getTopRatedMovies(page)

        return movieResponse;
    }

    suspend fun loadMovie(id: Long): ApiResponse<MovieProperty> {

        try {
            val response = movieService.getMovieDetails(id)
            if (response.isSuccessful) {

                return ApiResponse.create(response)
            } else {
                return ApiErrorResponse(response.errorBody().toString())
            }
        } catch (e: Exception) {

            return ApiResponse.create(e)
        }

    }

}

