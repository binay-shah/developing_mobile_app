package com.example.popularmovies.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponse (
    val page: Int,
    @Json(name="total_results")
    val totalResults: Int,
    @Json(name="total_pages")
    val totalPages: Int,
    @Json(name="results")
    val movies: List<MovieProperty>
)