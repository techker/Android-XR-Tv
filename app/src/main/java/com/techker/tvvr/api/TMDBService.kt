package com.techker.tvvr.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("page") page: Int = 1
    ): TMDBResponse

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): TMDBResponse

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): TMDBResponse

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): TMDBResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,  // Change from @Query to @Path
        @Query("api_key") apiKey: String,
    ): TMDBMovieDetails

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
    ): TMDBVideoResponse

}
    data class TMDBResponse(
    val page: Int,
    val results: List<TMDBMovie>,
    val total_pages: Int,
    val total_results: Int
)

data class TMDBMovie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val release_date: String,
    val vote_average: Double
)

data class TMDBMovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val genres: List<Genre>,
    val runtime: Int,
    val release_date: String,
    val poster_path: String?,
    val backdrop_path: String?
)

data class TMDBVideoResponse(
    val results: List<TMDBVideos>,
)

data class TMDBVideos(
    val iso_639_1:String?,
    val iso_3166_1:String?,
    val name:String,
    val key:String,
    val site:String,
    val size:Long,
    val type:String,
    val official: Boolean = true,
    val published_at:String,
    val id:String
)

data class Genre(
    val id: Int,
    val name: String
) 