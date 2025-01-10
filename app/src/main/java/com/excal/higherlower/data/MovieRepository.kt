package com.excal.higherlower.data

class MovieRepository(private val api:APIServices) {

    suspend fun getMovies(authToken:String,page:Int):List<Movie>{
        return try{
                val response=api.getRandomMovies(authToken = authToken,page=page)
                response.results
            }catch (e:Exception){
                emptyList()
            }

    }
}