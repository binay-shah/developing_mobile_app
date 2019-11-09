package com.example.popularmovies.ui.list.discover


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.popularmovies.R
import com.example.popularmovies.data.MovieRepository
import com.example.popularmovies.data.model.MovieProperty
import com.example.popularmovies.data.remote.network.MovieApiFilter
import com.example.popularmovies.data.remote.network.NetworkState
import com.example.popularmovies.storage.SharedPrefsManager
import com.example.popularmoviesapp.data.paging.MovieDataSourceFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import timber.log.Timber

const val PAGE_SIZE = 5
class DiscoverMoviesViewModel(val mMovieRepository: MovieRepository, private val sharedPrefsManager: SharedPrefsManager) : ViewModel() {


    val uiScope = CoroutineScope(Dispatchers.Main)

    val ioScope = CoroutineScope(Dispatchers.IO)

    // paging configuration
    val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(PAGE_SIZE)
        .build()


    //For Data
    private val   movieDataSource = MovieDataSourceFactory(mMovieRepository, getFilter(), ioScope)

    //observables
    val moviesPagedList: LiveData<PagedList<MovieProperty>> = LivePagedListBuilder(movieDataSource, config)
        .build()
    val networkState: LiveData<NetworkState> = Transformations.switchMap(movieDataSource.sourceLiveData) {
        it.networkState
    }

    private val _currentMovieFilter = MutableLiveData<MovieApiFilter>()
    val currentMovieFilter : LiveData<MovieApiFilter>
        get() = _currentMovieFilter

    private val _currentTitle = MutableLiveData<Int>()
    val currentTitle : LiveData<Int>
        get() = _currentTitle

    init {

        _currentMovieFilter.value = getFilter()
        _currentTitle.value = if (getFilter() == MovieApiFilter.POPULAR) R.string.popular else R.string.top_rated

    }

    fun refreshAllList() =  updateMovieFilter(getFilter())




    private val _navigateToSelectedMovie = MutableLiveData<MovieProperty>()

    val navigateToSelectedMovie : LiveData<MovieProperty>
        get() = _navigateToSelectedMovie

    fun retry() {
        movieDataSource.getSource()?.retryQuery?.invoke()
    }

    fun displayMovieDetails(movie : MovieProperty){
        _navigateToSelectedMovie.value = movie
    }

    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }

    /**
     * Saves filter [Filters.ResultSearchUsers] used to sort "search" request
     */
    fun saveFilter(filter: MovieApiFilter) {

        sharedPrefsManager.saveFilter(filter)
    }


    /**
     * Returns filter [Filters.ResultSearchUsers] used to sort "search" request
     */
    fun getFilter() =
        sharedPrefsManager.getFilter()

    fun updateMovieFilter(movieFilter: MovieApiFilter){

        Timber.d("updateMovieFilter: "+movieFilter)
        _currentTitle.value = if (getFilter() == MovieApiFilter.POPULAR) R.string.popular else R.string.top_rated
        //_currentMovieFilter.value = sharedPrefsManager.getFilter()
        movieDataSource.updateQuery(movieFilter)
    }

    override fun onCleared() {
        super.onCleared()
        uiScope.cancel()
        ioScope.cancel()
    }

}
