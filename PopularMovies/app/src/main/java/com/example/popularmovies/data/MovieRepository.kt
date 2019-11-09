package com.example.popularmovies.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.popularmovies.data.local.MovieLocalDataSource
import com.example.popularmovies.data.model.Movie
import com.example.popularmovies.data.model.MovieDetails
import com.example.popularmovies.data.model.MovieProperty
import com.example.popularmovies.data.model.MovieResponse
import com.example.popularmovies.data.remote.MovieRemoteDataSource
import com.example.popularmovies.data.remote.network.ApiResponse
import com.example.popularmovies.data.remote.network.MovieApiFilter
import com.example.popularmovies.data.remote.network.Resource
import com.example.popularmoviesapp.data.model.MoviesNetworkResult
import timber.log.Timber

class MovieRepository private constructor(private val mLocalDataSource: MovieLocalDataSource, private val mRemoteDataSource: MovieRemoteDataSource) : DataSource{


    override suspend fun loadMoviesFilteredBy(movieFilter: MovieApiFilter, page: Int): MovieResponse {
        return mRemoteDataSource.loadMoviesFilteredBy(movieFilter, page)

    }

    override  fun loadMovie(movieId: Long): LiveData<Resource<MovieDetails>> {

        return object: NetworkBoundResource<MovieDetails, MovieProperty>(){
            override fun loadFromDb(): LiveData<MovieDetails> {

               return  mLocalDataSource.getMovieDetails(movieId)
            }

            override fun shouldFetch(data: MovieDetails?): Boolean {

                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<MovieProperty>> {
                Log.d("Movie", "createCall ")
                return liveData {
                    val movie = mRemoteDataSource.loadMovie(movieId)

                    emit(movie)
                }
            }

            override fun saveCallResult(item: MovieProperty) {
                mLocalDataSource.saveMovie(item)
            }

        }.asLiveData()
    }

    fun getAllFavouriteMovies(): LiveData<List<Movie>>{
        return mLocalDataSource.getAllFavouriteMovies()
    }

    fun favouriteMovie(movie: Movie){
        mLocalDataSource.favouriteMovie(movie)
    }

    fun unFavouriteMovie(movie: Movie){
        mLocalDataSource.unFavouriteMovie(movie)
    }



    companion object {
        private lateinit var INSTANCE: MovieRepository
        fun getInstance(localDataSource: MovieLocalDataSource, remoteDataSource: MovieRemoteDataSource  ): MovieRepository {

           synchronized(MovieRepository::class.java) {
               if (!::INSTANCE.isInitialized) { INSTANCE = MovieRepository(localDataSource, remoteDataSource)
               }
           }
           return INSTANCE
       }
    }


}