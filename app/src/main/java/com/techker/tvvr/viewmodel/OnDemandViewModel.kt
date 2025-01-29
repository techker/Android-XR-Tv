package com.techker.tvvr.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techker.tvvr.api.TMDBMovieDetails
import com.techker.tvvr.data.Movies
import com.techker.tvvr.data.Videos
import com.techker.tvvr.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OnDemandViewModel(
    private val repository: MovieRepository
) : ViewModel() {
    private val _movies = MutableStateFlow<List<Movies>>(emptyList())
    val movies: StateFlow<List<Movies>> = _movies.asStateFlow()

    private val _movieDetails = MutableStateFlow<TMDBMovieDetails?>(null)
    val movieDetails: StateFlow<TMDBMovieDetails?> = _movieDetails.asStateFlow()

    private val _trailer= MutableStateFlow<List<Videos>>(emptyList())
    val trailers: StateFlow<List<Videos>> = _trailer.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadMovies()
    }

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val details = repository.getMovieDetails(movieId)
                _movieDetails.value = details
            } catch (e: Exception) {
                _isLoading.value = false
                Log.e("MovieViewModel", "Error fetching movie details", e)
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun getSelectedTrailer(movieId:Int){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _trailer.value = repository.getMovieVideos(movie_id = movieId)
            } catch (e: Exception) {
                // Handle error
                Log.d("VOD", "loadMovies error ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _movies.value = repository.getMovies()
            } catch (e: Exception) {
                // Handle error
                Log.d("VOD","loadMovies error ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
} 