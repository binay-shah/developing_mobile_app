package com.example.popularmovies.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.popularmovies.utils.BACKDROP_URL
import com.example.popularmovies.utils.IMAGE_URL
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@JsonClass(generateAdapter = true)
data class MovieProperty (

    val id : Long,
    val title : String,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "vote_count")
    val voteCount: Int,
    val popularity: Double,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val originalTitle: String,
    val genres : List<Genre>?,
    @Json(name = "backdrop_path")
    val backdropPath: String? ,
    val overview: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "videos")
    val trailersResponse: TrailersResponse?

)





/**
 * Convert Network results to database objects
 */
fun MovieProperty.asDatabaseModel(): Movie {
    return Movie(
        id = this.id ,
        title = this.title,
        voteAverage = this.voteAverage,
        posterPath = this.posterPath,
        voteCount = this.voteCount,
        popularity = this.popularity,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        genres  = this.genres,
        backdropPath = this.backdropPath,
        overview = this.overview ,
        releaseDate = this.releaseDate,
        isFavourite = 0
    )
}


