package com.example.popularmovies.utils

import com.example.popularmovies.data.remote.network.MovieApiFilter

fun convertFilterToIndex(filter: MovieApiFilter) = when (filter) {
    MovieApiFilter.POPULAR -> 0
    MovieApiFilter.TOP_RATED -> 1

}

fun convertIndexToFilter(index: Int) = when (index) {
    0 -> MovieApiFilter.POPULAR
    1 -> MovieApiFilter.TOP_RATED
    else -> throw IllegalStateException("Index not recognized")
}