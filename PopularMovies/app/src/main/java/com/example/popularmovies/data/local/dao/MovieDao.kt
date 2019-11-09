package com.example.popularmovies.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.popularmovies.data.model.Movie
import com.example.popularmovies.data.model.MovieDetails
import com.example.popularmovies.data.model.MovieProperty

@Dao
interface MovieDao {

    @Query("select * from movies")
    fun loadMovies(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movie: Movie?)

    @Query("SELECT * FROM movies where is_favourite = 1")
    fun getAllFavoriteMovies(): LiveData<List<Movie>>

    @Transaction
    @Query("select * from movies where id = :movieId")
    fun getMovieDetails(movieId: Long) : LiveData<MovieDetails>

    @Query("update movies set is_favourite = 1 where id = :movieId")
    fun favouriteMovie(movieId: Long)

    @Query("update movies set is_favourite = 0 where id = :movieId")
    fun unFavouriteMovie(movieId: Long)

}