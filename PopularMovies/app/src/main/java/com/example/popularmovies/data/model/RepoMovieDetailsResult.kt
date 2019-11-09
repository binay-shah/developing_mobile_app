package com.example.popularmovies.data.model

import androidx.lifecycle.LiveData
import com.example.popularmovies.data.remote.network.NetworkState

data class RepoMovieDetailsResult(
    val data : LiveData<MovieProperty>, val networkState: LiveData<NetworkState>
)