package com.techker.tvvr.data

data class Movie(
    val id: String,
    val posterUri: String,
    val name: String,
    val description: String
)

data class Movies(
    val id: String,
    val name: String,
    val description: String,
    val posterUri: String,
    val genres: List<String>,
    val rating: String,
    val duration: Int, // in minutes
    val releaseYear: Int,
    val director: String,
    val cast: List<String>,
    val streamingUrl: String? = null,
    val trailerUrl: String? = null,
    val thumbnailUri: String? = null,
    val isNew: Boolean = false,
    val isFeatured: Boolean = false
)
data class Videos(
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

data class Asset(val id: String, val title: String, val thumbnailUrl: String)

data class AssetRow(val title: String, val assets: List<Asset>)

data class TimeSlot(
    val time: String,
    val startHour: Float
)

data class Program(
    val title: String,
    val startTime: Float, // Changed from Int to Float to handle half hours
    val duration: Float, // Duration in hours (can be 0.5, 1, 1.5, etc.)
    val isRecording: Boolean = false,  // Add this property
    val description: String = "Program Short Description", // Add default description
    val programPoster: String = "Program Poster" // Add default poster
)

data class Channel(
    val id: String,
    val name: String,
    val logo: String,
    val programs: List<Program>
)

data class SelectedProgram(
    val title: String,
    val description: String = "No description available",
    val rating: String = "TV-14",
    val imageUrl: String = "",
    val startTime: Float,
    val endTime: Float
)