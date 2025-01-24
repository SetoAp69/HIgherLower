package com.excal.higherlower.presentation.gamedata

data class GameData(
    val uid:String,
    val blitz:Int,
    val normal:Int,
){
    constructor() :this("",0,0)
}
