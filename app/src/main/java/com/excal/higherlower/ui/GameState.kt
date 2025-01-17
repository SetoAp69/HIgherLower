package com.excal.higherlower.ui

data class GameState(
    val isAnswered: Boolean = false,
    val isCorrect: Boolean = false,
    val score: Int = 0,
    val movieIndex: Int = 0,
    val isOutOfMovie: Boolean = false,
    val page: Int = 0,
    val isFinish:Boolean=false
)