package com.alaazarifa.moviesapptestyassir.data.repository

import com.alaazarifa.moviesapptestyassir.data.api.client.RetrofitClient
import com.alaazarifa.moviesapptestyassir.data.api.service.MoviesService
import com.alaazarifa.moviesapptestyassir.data.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

open class MoviesRepo(
    private var service: MoviesService = RetrofitClient.getService(MoviesService::class.java),
) {

    companion object {
        private var INSTANCE: MoviesRepo? = null
        fun getInstance() = INSTANCE ?: MoviesRepo().also { INSTANCE = it }
    }

    fun getTrending(): Flow<List<Movie>> {
        return flow {
            emit(service.getTrending())
        }.map {
            it.movies
        }
    }

    fun getDetails(
        movieID: Long,
    ): Flow<Movie> {
        return flow {
            emit(service.getDetails(movieID))
        }
    }

}

