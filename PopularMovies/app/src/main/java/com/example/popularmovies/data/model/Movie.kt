package com.example.popularmovies.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="movies")
data class Movie(
    @PrimaryKey
    val id : Long,
    val title : String,
    @ColumnInfo(name="vote_average")
    val voteAverage: Double,
    @ColumnInfo(name="poster_path")
    val posterPath: String?,
    @ColumnInfo(name="vote_count")
    val voteCount: Int,
    val popularity: Double,
    @ColumnInfo(name="original_language")
    val originalLanguage: String,
    @ColumnInfo(name="original_title")
    val originalTitle: String,
    val genres : List<Genre>?,
    @ColumnInfo(name="backdrop_path")
    val backdropPath: String? ,
    val overview: String,
    @ColumnInfo(name="release_date")
    val releaseDate: String,
    @ColumnInfo(name="is_favourite")
    val isFavourite: Int

)

