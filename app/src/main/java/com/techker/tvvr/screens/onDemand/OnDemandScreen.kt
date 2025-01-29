package com.techker.tvvr.screens.onDemand

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.techker.tvvr.TvVrApplication
import com.techker.tvvr.data.Movies
import com.techker.tvvr.repository.MovieRepository
import com.techker.tvvr.screens.navigation.NavigationTopBar
import com.techker.tvvr.viewmodel.OnDemandViewModel

@Composable
fun OnDemandScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: OnDemandViewModel = viewModel(
        factory = OnDemandViewModelFactory((LocalContext.current.applicationContext as TvVrApplication).movieRepository)
    )
) {
    val movies by viewModel.movies.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        } else if (movies.isEmpty()) {
            Text(
                text = "No movies available",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            // Get all unique genres from the movies
            val allGenres = movies.flatMap { it.genres }.distinct().sorted()
            
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp)
            ) {
                // Featured section
                val featuredMovies = movies.filter { it.isFeatured }
                if (featuredMovies.isNotEmpty()) {
                    item {
                        CategoryRow(
                            title = "Featured",
                            movies = featuredMovies,
                            onMovieClick = { movieId ->
                                navController.navigate("content/$movieId")
                            },
                            isFeatured = true
                        )
                    }
                }

                // New Releases section
                val newMovies = movies.filter { it.isNew }
                if (newMovies.isNotEmpty()) {
                    item {
                        CategoryRow(
                            title = "New Releases",
                            movies = newMovies,
                            onMovieClick = { movieId ->
                                navController.navigate("content/$movieId")
                            }
                        )
                    }
                }

                // Genre categories
                items(allGenres) { genre ->
                    val genreMovies = movies.filter { it.genres.contains(genre) }
                    if (genreMovies.isNotEmpty()) {
                        CategoryRow(
                            title = genre,
                            movies = genreMovies,
                            onMovieClick = { movieId ->
                                navController.navigate("content/$movieId")
                            }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }

        NavigationTopBar(
            navController = navController,
            onAvatarClick = {
                navController.navigate("profile")
            }
        )
    }
}

@Composable
private fun CategoryRow(
    title: String,
    movies: List<Movies>,
    onMovieClick: (String) -> Unit,
    isFeatured: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(movies) { movie ->
                MovieCard(
                    movie = movie,
                    onClick = { onMovieClick(movie.id) },
                    isFeatured = isFeatured
                )
            }
        }
    }
}

@Composable
private fun MovieCard(
    movie: Movies,
    onClick: () -> Unit,
    isFeatured: Boolean = false
) {
    Box(
        modifier = Modifier
            .width(if (isFeatured) 300.dp else 200.dp)
            .height(if (isFeatured) 450.dp else 300.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = movie.posterUri),
            contentDescription = movie.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay at the bottom
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.7f)
                        ),
                        startY = 300f
                    )
                )
        )

        // Movie title at the bottom
        Text(
            text = movie.name,
            style = if (isFeatured) 
                MaterialTheme.typography.headlineSmall 
            else 
                MaterialTheme.typography.bodyLarge,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp)
        )
    }
}

// Add ViewModel Factory
class OnDemandViewModelFactory(
    private val repository: MovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OnDemandViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OnDemandViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Preview
@Composable
fun ShowVodPreview(){
    val navController = rememberNavController() // Create a mock NavController
    OnDemandScreen(navController)
}