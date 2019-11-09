package com.example.popularmovies.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class MovieDetails (

    @Embedded
    val movie: Movie,

    @Relation(parentColumn = "id",
        entityColumn = "movieId")
    val trailerList: List<Trailer> = ArrayList()
    )