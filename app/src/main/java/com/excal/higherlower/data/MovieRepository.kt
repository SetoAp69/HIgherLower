package com.excal.higherlower.data

class MovieRepository(private val api:APIServices) {

    suspend fun getMovies(authToken:String):List<Movie>{
        return try{
                val response=api.getRandomMovies(authToken = authToken)
                response.results
            }catch (e:Exception){
                emptyList()
            }

    }
}