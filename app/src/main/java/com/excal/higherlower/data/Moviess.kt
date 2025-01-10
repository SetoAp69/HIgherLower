package com.excal.higherlower.data

data class MovieResponses(
    val results: List<Movie>, // Matches JSON "results"
    val total_pages: Int,     // Matches JSON "total_pages"
    val total_results: Int    // Matches JSON "total_results"
)

data class Moviess(
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val popularity: Double
)
