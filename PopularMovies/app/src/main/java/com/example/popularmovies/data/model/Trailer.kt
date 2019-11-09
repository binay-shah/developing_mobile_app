package com.example.popularmovies.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName="trailers",
    foreignKeys = arrayOf(ForeignKey(entity = Movie::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("movieId"))))
data class Trailer(
    var movieId: Long?,
    @PrimaryKey
    val id: String,
    val iso_3166_1: String,
    val iso_639_1: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String
)