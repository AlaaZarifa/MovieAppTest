package com.alaazarifa.moviesapptestyassir.data.api.service

import com.alaazarifa.moviesapptestyassir.data.model.Movie
import com.alaazarifa.moviesapptestyassir.data.model.MoviesListResponse
import retrofit2.Response
import retrofit2.http.*

interface MoviesService {

    @GET("discover/movie")
    suspend fun getTrending(): MoviesListResponse

    @GET("movie/{movie_id}")
    suspend fun getDetails(@Path("movie_id") id: Long): Movie

}