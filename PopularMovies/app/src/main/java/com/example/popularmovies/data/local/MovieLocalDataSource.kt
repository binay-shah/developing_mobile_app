package com.example.popularmovies.data.local

import androidx.lifecycle.LiveData
import com.example.popularmovies.data.model.*


class MovieLocalDataSource private constructor(val movieDatabase: MovieDatabase) {

    fun saveMovie(movie: MovieProperty){
        movieDatabase.movieDao.insertMovie(movie.asDatabaseModel());
        insertTrailers(movie.trailersResponse?.results, movie.id)
    }

    fun insertTrailers(trailers: List<Trailer>?, movieId: Long){
        for(trailer in trailers!!){
            trailer.movieId = movieId
        }
        movieDatabase.trailersDao.insertAllTrailers(trailers)

    }

    fun getAllFavouriteMovies(): LiveData<List<Movie>> {
        return movieDatabase.movieDao.getAllFavoriteMovies()
    }

    fun getMovieDetails(movieId: Long) : LiveData<MovieDetails>{
        return movieDatabase.movieDao.getMovieDetails(movieId)
    }

    fun favouriteMovie(movie: Movie){
        movieDatabase.movieDao.favouriteMovie(movie.id)
    }

    fun unFavouriteMovie(movie: Movie){
        movieDatabase.movieDao.unFavouriteMovie(movie.id)
    }


    companion object {
        private lateinit var INSTANCE: MovieLocalDataSource
        fun getInstance( movieDatabase: MovieDatabase): MovieLocalDataSource {

            synchronized(MovieDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = MovieLocalDataSource(movieDatabase)
                }
            }
            return INSTANCE
        }
    }


}