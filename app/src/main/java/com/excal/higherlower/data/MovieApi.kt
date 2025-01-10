package com.excal.higherlower.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
private val BASE_URL=("https://api.themoviedb.org/3/")
///3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&with_original_language=en/

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

object MovieApi {
    val retrofitServices:APIServices by lazy{
        retrofit.create(APIServices::class.java)
    }
}