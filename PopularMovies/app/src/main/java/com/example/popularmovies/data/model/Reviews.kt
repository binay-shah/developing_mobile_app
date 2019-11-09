package com.example.popularmovies.data.model

data class Reviews(
    val page: Int,
    val results: List<ResultX>,
    val total_pages: Int,
    val total_results: Int
)