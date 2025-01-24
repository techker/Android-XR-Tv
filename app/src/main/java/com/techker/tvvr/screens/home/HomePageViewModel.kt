package com.techker.tvvr.screens.home

import androidx.lifecycle.ViewModel
import com.techker.tvvr.data.MockData.homePageCarouselItems
import com.techker.tvvr.data.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomePageViewModel : ViewModel() {

    private val _movieLists = MutableStateFlow<MutableList<Movie>>(mutableListOf())
    val movieLists: StateFlow<MutableList<Movie>> = _movieLists

    init {
        _movieLists.value = homePageCarouselItems
    }


}