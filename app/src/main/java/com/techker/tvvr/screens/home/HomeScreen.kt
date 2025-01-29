package com.techker.tvvr.screens.home

import HomeViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.techker.tvvr.TvVrApplication
import com.techker.tvvr.data.Channel
import com.techker.tvvr.data.Movies
import com.techker.tvvr.repository.MovieRepository
import com.techker.tvvr.screens.navigation.NavigationTopBar
import com.techker.tvvr.widgets.AutoScrollingCarousel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory((LocalContext.current.applicationContext as TvVrApplication).movieRepository)
    )
) {
    val trendingMovies by viewModel.trendingMovies.collectAsState(initial = emptyList())
    val nowPlayingMovies by viewModel.nowPlayingMovies.collectAsState(initial = emptyList())
    val upcomingMovies by viewModel.upcomingMovies.collectAsState(initial = emptyList())
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
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp)
            ) {
                // Auto-scrolling carousel with trending movies
                item {
                    AutoScrollingCarousel(
                        items = trendingMovies,
                        onItemClick = { movie ->
                            navController.navigate("content/${movie.id}")
                        }
                    ) { movie ->
                        CarouselItemContent(
                            item = movie,
                            onClick = { navController.navigate("content/${movie.id}") }
                        )
                    }
                }

                // Continue Watching section
                item {
                    Text(
                        text = "Continue Watching",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
                    )
                    ContinueMoviesCarousel(
                        movies = nowPlayingMovies,
                        onMovieClick = { movieId ->
                            navController.navigate("content/$movieId")
                        }
                    )
                }

                // Movies section
                item {
                    Text(
                        text = "Movies",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
                    )
                    MoviesCarousel(
                        movies = upcomingMovies,
                        onMovieClick = { movieId ->
                            navController.navigate("content/$movieId")
                        }
                    )
                }

                // TV Channels section
                item {
                    Text(
                        text = "TV Channels",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
                    )
                    TvChannelsCarousel(
                        movies = trendingMovies.take(5), // Using trending movies for TV channels
                        onMovieClick = { movieId ->
                            navController.navigate("content/$movieId")
                        }
                    )
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
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
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
                    brush = Brush.verticalGradient(
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

@Composable
fun ContinueMoviesCarousel(
    movies: List<Movies>,
    onMovieClick: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(movies) { movie ->
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(100.dp)
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable { onMovieClick(movie.id) }
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = movie.posterUri),
                    contentDescription = movie.name,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun MoviesCarousel(
    movies: List<Movies>,
    onMovieClick: (String) -> Unit
) {
    val lazyListState = rememberLazyListState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .scrollable(
                    orientation = Orientation.Horizontal,
                    state = rememberScrollableState { delta ->
                        delta * 0.5f
                    }
                ),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            flingBehavior = rememberSnapFlingBehavior(lazyListState)
        ) {
            items(
                items = movies,
                key = { it.id }
            ) { movie ->
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(100.dp)
                        .clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable(
                            enabled = true,
                            onClickLabel = "Select ${movie.name}",
                            onClick = { onMovieClick(movie.id) }
                        )
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = movie.posterUri),
                        contentDescription = movie.name,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun TvChannelsCarousel(
    movies: List<Movies>,
    onMovieClick: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(movies) { movie ->
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(100.dp)
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable { onMovieClick(movie.id) }
            ) {
                AsyncImage(
                    model = movie.posterUri,
                    contentDescription = movie.name,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun ChannelRow(
    channels: List<Channel>,
    onChannelClick: (Channel) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(channels) { channel ->
            ChannelIcon(
                channel = channel,
                onClick = { 
                    onChannelClick(channel)
                }
            )
        }
    }
}

@Composable
private fun CarouselItemContent(
    item: Movies,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = item.posterUri,
            contentDescription = item.name,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
        
        // Add gradient overlay at the bottom
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.7f)
                        ),
                        startY = 300f
                    )
                )
        )
        
        // Add title at the bottom
        Text(
            text = item.name,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        )
    }
}

@Composable
fun ChannelIcon(
    channel: Channel,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .size(72.dp) // Icon size
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = channel.logo),
            contentDescription = channel.name,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape) // Makes the icon round
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape) // Optional border
                .background(MaterialTheme.colorScheme.surface) // Optional background
        )
//        Spacer(modifier = Modifier.height(4.dp))
//        Text(
//            text = channel.name,
//            style = MaterialTheme.typography.bodySmall,
//            textAlign = TextAlign.Center,
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis
//        )
    }
}

@Preview(
    name = "XR Panel Preview",
    showBackground = true,
    widthDp = 1280,
    heightDp = 720
)
@Composable
fun PreviewHome(){
    val navController = rememberNavController() // Create a mock NavController
    NavigationTopBar(navController) {}
    HomeScreen(navController = navController)
}

// Add ViewModel Factory
class HomeViewModelFactory(
    private val repository: MovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}