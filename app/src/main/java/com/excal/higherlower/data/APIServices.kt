package com.excal.higherlower.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIServices {
    @GET("discover/movie")
    suspend fun getRandomMovies(
        @Header("Authorization") authToken: String,
        @Header("accept") acceptHeader: String = "application/json",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int ,
        @Query("sort_by") sortBy: String = "popularity.desc"
    ): MovieResponse
}
