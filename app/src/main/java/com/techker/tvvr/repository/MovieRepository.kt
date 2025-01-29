package com.techker.tvvr.repository

import android.util.Log
import com.techker.tvvr.api.TMDBMovie
import com.techker.tvvr.api.TMDBMovieDetails
import com.techker.tvvr.api.TMDBService
import com.techker.tvvr.api.TMDBVideos
import com.techker.tvvr.data.GenreMapping
import com.techker.tvvr.data.Movies
import com.techker.tvvr.data.Videos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(
    private val tmdbService: TMDBService,
    private val apiKey: String
) {
    suspend fun getMovies(): List<Movies> = withContext(Dispatchers.IO) {
        try {
            val response = tmdbService.getMovies(apiKey)
            Log.d("MovieRepository", "Fetched ${response.results.size} movies")
            response.results.map { tmdbMovie ->
                Movies(
                    id = tmdbMovie.id.toString(),
                    name = tmdbMovie.title,
                    description = tmdbMovie.overview,
                    posterUri = if (tmdbMovie.poster_path != null) 
                        "https://image.tmdb.org/t/p/w500${tmdbMovie.poster_path}"
                    else 
                        "https://i.ebayimg.com/images/g/x-UAAOSw4upkQU4s/s-l1200.jpg", // default poster URL
                    genres = GenreMapping.getGenreNames(tmdbMovie.genre_ids),
                    rating = "PG-13",
                    duration = 120,
                    releaseYear = tmdbMovie.release_date.split("-")[0].toInt(),
                    director = "",
                    cast = listOf(),
                    thumbnailUri = if (tmdbMovie.backdrop_path != null)
                        "https://image.tmdb.org/t/p/w200${tmdbMovie.backdrop_path}"
                    else
                        "",
                    isNew = true,
                    isFeatured = tmdbMovie.vote_average > 7.0 // Example criteria for featured movies
                )
            }
        } catch (e: Exception) {
            Log.e("MovieRepository", "Error fetching movies", e)
            emptyList()
        }
    }

    suspend fun getMovieDetails(movie_id: Int): TMDBMovieDetails = withContext(Dispatchers.IO) {
        try {
            tmdbService.getMovieDetails(movieId = movie_id, apiKey = apiKey)
        } catch (e: Exception) {
            Log.e("MovieRepository", "Error fetching movie details", e)
            TMDBMovieDetails(0, "", "", emptyList(), 0, "", null, null) // Provide a default fallback return
        }
    }

    suspend fun getMovieVideos(movie_id:Int): List<Videos> = withContext(Dispatchers.IO) {
        try {
            val response = tmdbService.getMovieVideos(movie_id,apiKey)
            mapTMDBVideosToMovies(response.results)
        } catch (e: Exception) {
            Log.e("MovieRepository", "Error fetching Trailer", e)
            emptyList()
        }
    }

    suspend fun getTrendingMovies(): List<Movies> = withContext(Dispatchers.IO) {
        try {
            val response = tmdbService.getTrendingMovies(apiKey)
            mapTMDBMoviesToMovies(response.results)
        } catch (e: Exception) {
            Log.e("MovieRepository", "Error fetching trending movies", e)
            emptyList()
        }
    }

    suspend fun getNowPlayingMovies(): List<Movies> = withContext(Dispatchers.IO) {
        try {
            val response = tmdbService.getNowPlaying(apiKey)
            mapTMDBMoviesToMovies(response.results)
        } catch (e: Exception) {
            Log.e("MovieRepository", "Error fetching now playing movies", e)
            emptyList()
        }
    }

    suspend fun getUpcomingMovies(): List<Movies> = withContext(Dispatchers.IO) {
        try {
            val response = tmdbService.getUpcoming(apiKey)
            mapTMDBMoviesToMovies(response.results)
        } catch (e: Exception) {
            Log.e("MovieRepository", "Error fetching upcoming movies", e)
            emptyList()
        }
    }

    private fun mapTMDBMoviesToMovies(tmdbMovies: List<TMDBMovie>): List<Movies> {
        return tmdbMovies.map { tmdbMovie ->
            Movies(
                id = tmdbMovie.id.toString(),
                name = tmdbMovie.title,
                description = tmdbMovie.overview,
                posterUri = if (tmdbMovie.poster_path != null) 
                    "https://image.tmdb.org/t/p/w500${tmdbMovie.poster_path}"
                else 
                    "https://i.ebayimg.com/images/g/x-UAAOSw4upkQU4s/s-l1200.jpg",
                genres = GenreMapping.getGenreNames(tmdbMovie.genre_ids),
                rating = "PG-13",
                duration = 120,
                releaseYear = tmdbMovie.release_date.split("-")[0].toInt(),
                director = "",
                cast = listOf(),
                thumbnailUri = if (tmdbMovie.backdrop_path != null)
                    "https://image.tmdb.org/t/p/w200${tmdbMovie.backdrop_path}"
                else
                    "",
                isNew = true,
                isFeatured = tmdbMovie.vote_average > 7.0
            )
        }
    }

    private fun mapTMDBVideosToMovies(tmdbMovies: List<TMDBVideos>): List<Videos> {
        return tmdbMovies.map { tmdbMovie ->
            Videos(
                iso_639_1 = tmdbMovie.iso_639_1,
                iso_3166_1 = tmdbMovie.iso_3166_1,
                name =tmdbMovie.name,
                key = tmdbMovie.key,
                site = tmdbMovie.site,
                size = tmdbMovie.size,
                type = tmdbMovie.type,
                official = tmdbMovie.official,
                published_at = tmdbMovie.published_at,
                id = tmdbMovie.id
            )
        }
    }
}