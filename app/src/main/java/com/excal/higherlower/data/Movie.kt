package com.excal.higherlower.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
):Parcelable


val dummyListMovie=listOf(
    Movie(
      adult = false,
        backdrop_path ="/oHPoF0Gzu8xwK4CtdXDaWdcuZxZ.jpg",
        genre_ids = listOf(12,10751,16),
        id=762509,
        original_language = "en",
        original_title="Mufasa",
        overview="Nice",
        popularity = 6.9,
        release_date = "14/14/14",
        title = "Mufasa",
        video=false,
        vote_average = 6.9,
        vote_count = 100,
        poster_path = "/jbOSUAWMGzGL1L4EaUF8K6zYFo7.jpg"

    ),
    Movie(
        adult = false,
        backdrop_path ="/oHPoF0Gzu8xwK4CtdXDaWdcuZxZ.jpg",
        genre_ids = listOf(12,10751,16),
        id=762509,
        original_language = "en",
        original_title="Mufasa",
        overview="Nice",
        popularity = 6.9,
        release_date = "14/14/14",
        title = "Mufasa",
        video=false,
        vote_average = 6.9,
        vote_count = 100,
        poster_path = "/jbOSUAWMGzGL1L4EaUF8K6zYFo7.jpg"

    )
)