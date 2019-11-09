package com.example.popularmovies.data.remote.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.popularmovies.data.MovieRepository
import com.example.popularmovies.data.model.MovieProperty
import com.example.popularmovies.data.remote.network.*
import kotlinx.coroutines.*
import timber.log.Timber


class MoviePageKeyedDataSource(private val mMovieRespository: MovieRepository,
                               private val movieFilter: MovieApiFilter,
                               private val scope: CoroutineScope) : PageKeyedDataSource<Int, MovieProperty>(){

    companion object{

        const val  FIRST_PAGE = 1
    }

    //for data ---

    private var supervisorJob = SupervisorJob()
    private val _networkState = MutableLiveData<NetworkState>()
    var retryQuery: (() -> Any)? = null // Keep reference of the last query (to be able to retry it if necessary)


    //OVERRIDE ---
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieProperty>) {
        Timber.d("loadInitial")
        retryQuery =  { loadInitial(params, callback)   }

        executeQuery(1, params.requestedLoadSize){
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieProperty>) {
        Timber.d("loadAfter")
        val page = params.key
        retryQuery = { loadAfter(params, callback) }
        executeQuery(page, params.requestedLoadSize){
            callback.onResult(it,  page+1)
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieProperty>) {
        // ignored, since we only ever append to our initial load
    }

    //UTILS
    private fun executeQuery(page: Int, perPage: Int, callback:(List<MovieProperty>) -> Unit) {
        _networkState.postValue(NetworkState.LOADING)
        scope.launch(getJobErrorHandler() + supervisorJob) {
            //delay(2000) // To handle user typing case
            val movies = mMovieRespository.loadMoviesFilteredBy(movieFilter, page).movies
            retryQuery = null
            _networkState.postValue(NetworkState.LOADED)
            callback(movies)
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        Timber.e( "An error happened: ${e.printStackTrace()}")
        _networkState.postValue(NetworkState.FAILED)
    }

    override fun invalidate() {
        Timber.d("invalidate")
        super.invalidate()
        supervisorJob.cancelChildren() // Cancel possible running job to only keep last result searched by user
    }

    // PUBLIC API ---

    fun refresh() = this.invalidate()

    // The external immutable LiveData for the response String
    val networkState: LiveData<NetworkState>
        get() = _networkState


    fun retryFailedQuery() {
        val prevQuery = retryQuery
        retryQuery = null
        prevQuery?.invoke()
    }


}