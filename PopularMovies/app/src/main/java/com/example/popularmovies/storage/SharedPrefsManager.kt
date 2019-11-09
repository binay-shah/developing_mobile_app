package com.example.popularmovies.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.popularmovies.data.remote.network.MovieApiFilter
import com.example.popularmovies.extensions.setValue
import java.lang.IllegalStateException

class SharedPrefsManager(private val context: Context) {

    private fun get(): SharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

    /**
     * Returns [MovieApiFilter] saved in [SharedPreferences]
     * This filter will be used to sort "search" queries
     */
    fun getFilter(): MovieApiFilter {
        val value = get().getString(KEY_FILTERS, MovieApiFilter.POPULAR.value)
        return when (value) {
            MovieApiFilter.POPULAR.value -> MovieApiFilter.POPULAR
            MovieApiFilter.TOP_RATED.value -> MovieApiFilter.TOP_RATED
            else -> throw IllegalStateException("Filter not recognized")
        }
    }

    /**
     * Saves [Filters.ResultSearchUsers] in [SharedPreferences]
     * This filter will be used to sort "search" queries
     */
    fun saveFilter(filters: MovieApiFilter) {
        get().setValue(KEY_FILTERS, filters.value)
    }

    companion object {
        private const val SP_NAME = "MovieAppPrefs"
        private const val KEY_FILTERS = "KEY_FILTERS"
    }
}