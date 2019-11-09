package com.example.popularmoviesapp.data.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.popularmovies.data.model.MovieProperty
import com.example.popularmovies.data.remote.network.NetworkState
import com.example.popularmovies.data.remote.paging.MoviePageKeyedDataSource


data class MoviesNetworkResult (
    val data : LiveData<PagedList<MovieProperty>>,
    val networkState: LiveData<NetworkState>,
    val sourceLiveData: LiveData<MoviePageKeyedDataSource>
)

