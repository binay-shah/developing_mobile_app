package com.example.popularmovies.ui.details


import androidx.lifecycle.*
import com.example.popularmovies.data.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MovieDetailViewModel(val mMovieRepository: MovieRepository) : ViewModel() {

    private var _isFavourite = false

    val isFavourite: Boolean
        get() = _isFavourite




            /**
     * Request a toast by setting this value to true.
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private var _showSnackbarEvent = MutableLiveData<Boolean>()


    /**
     * If this is true, immediately `show()` a toast and call `doneShowingSnackbar()`.
     */
    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    fun setFavourite(favourite: Boolean){
        _isFavourite = favourite
    }




    private val _selectedMovieId = MutableLiveData<Long>()






     val movieDetailsResult = Transformations.switchMap(_selectedMovieId) {

         mMovieRepository.loadMovie(it)
//        liveData {
//            //emit(Resource.loading(null))
//            val data = mMovieRepository.loadMovie(it).value
//            emit(data)
//
//        }
    }


    fun setMovieId(movieId: Long) {
        _selectedMovieId.value = movieId
    }

    fun onFavouriteClick(id: Long){

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                if(!isFavourite) {
                    mMovieRepository.favouriteMovie(movieDetailsResult.value?.data?.movie!!)
                    _isFavourite = true

                }
                else{
                    mMovieRepository.unFavouriteMovie(movieDetailsResult.value?.data?.movie!!)
                    _isFavourite  = false
                }
            }
        }

        _showSnackbarEvent.value = true
    }

}




