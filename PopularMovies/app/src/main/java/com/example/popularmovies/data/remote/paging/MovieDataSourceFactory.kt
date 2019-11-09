package com.example.popularmoviesapp.data.paging


import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.popularmovies.data.MovieRepository
import com.example.popularmovies.data.model.MovieProperty
import com.example.popularmovies.data.remote.network.MovieApiFilter
import com.example.popularmovies.data.remote.paging.MoviePageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber


/**
 * A simple data source factory provides a way to observe the last created data source.*
 *
 */

class MovieDataSourceFactory(private val movieRepository: MovieRepository,
                             private var filter: MovieApiFilter,
                             private val scope: CoroutineScope) : DataSource.Factory<Int, MovieProperty>(){

     val sourceLiveData = MutableLiveData<MoviePageKeyedDataSource>()

    override fun create(): DataSource<Int, MovieProperty> {
        Timber.d("Datasource created: "+ filter)
        val movieDataSource = MoviePageKeyedDataSource(movieRepository, filter, scope)
        this.sourceLiveData.postValue(movieDataSource)
        return movieDataSource
    }

    // --- PUBLIC API

    fun getFilter() = filter

    fun getSource() = sourceLiveData.value

    fun updateQuery( filter: MovieApiFilter){
        this.filter = filter
        getSource()?.refresh()
    }

}

