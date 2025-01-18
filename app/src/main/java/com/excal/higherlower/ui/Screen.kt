package com.excal.higherlower.ui

sealed class Screen(val route:String) {
    object MainScreen : Screen("main")
    object SignInScreen:Screen("signin")
    object GameOverScreen:Screen("game_over")
    object NormalMode:Screen("normal_mode")
    object BlitzMode:Screen("blitz_mode")

    fun withArgs(vararg args:String):String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}