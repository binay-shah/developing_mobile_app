package com.example.popularmovies.data.remote.network

//enum class Status{ LOADING, ERROR, DONE}

data class NetworkState(val status: Status, val msg: String?){

    companion object{
         val LOADED = NetworkState(
             Status.SUCCESS,
             null
         )
         val LOADING = NetworkState(
             Status.LOADING,
             null
         )

        val FAILED = NetworkState(
            Status.ERROR,
            null
        )

        fun error(msg: String): NetworkState {
            return NetworkState(
                Status.ERROR,
                msg
            );
        }
    }
}

