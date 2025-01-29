package com.techker.tvvr

import android.app.Application
import com.techker.tvvr.api.TMDBService
import com.techker.tvvr.data.Constants.TMDB_API_KEY
import com.techker.tvvr.data.Constants.TMDB_BASE_URL
import com.techker.tvvr.repository.MovieRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TvVrApplication: Application() {

    val tmdbService: TMDBService = Retrofit.Builder()
        .baseUrl(TMDB_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TMDBService::class.java)

    val movieRepository = MovieRepository(tmdbService, TMDB_API_KEY)
}