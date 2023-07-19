package com.alaazarifa.moviesapptestyassir.ui.movie_details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.alaazarifa.moviesapptestyassir.data.api.client.UiState
import com.alaazarifa.moviesapptestyassir.data.model.Movie
import com.alaazarifa.moviesapptestyassir.data.repository.MoviesRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MovieDetailsViewModel(app: Application) : AndroidViewModel(app) {

    private var repo = MoviesRepo.getInstance()

    private val _uiState = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Movie>>> = _uiState


    fun fetchDetails(id: Long) {
        viewModelScope.launch {
            repo.getDetails(id)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect { movie ->
                    _uiState.value = UiState.Success(listOf(movie))
                }
        }
    }


}