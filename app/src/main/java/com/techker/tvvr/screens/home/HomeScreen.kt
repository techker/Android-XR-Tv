package com.techker.tvvr.screens.home

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.techker.tvvr.data.Channel
import com.techker.tvvr.data.MockData.homePageCarouselItems
import com.techker.tvvr.data.MockData.sampleMovies
import com.techker.tvvr.data.Movie
import com.techker.tvvr.screens.navigation.NavigationTopBar
import com.techker.tvvr.widgets.AutoScrollingCarousel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {

        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp) // Add padding for the NavigationTopBar
        ) {
            // Carousel section
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(top = 16.dp)
                ) {
                    AutoScrollingCarousel(
                        items = homePageCarouselItems,
                        itemWidth = LocalConfiguration.current.screenWidthDp.dp - 32.dp, // Full width minus padding
                        itemSpacing = 16.dp,
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) { item ->
                        CarouselItemContent(
                            item = item,
                            onClick = {
                                navController.navigate("content/${item.id}") {
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }

            // What's New section
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "What's New",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start,
                    color = Color.White,
                    modifier = Modifier.padding(start = 25.dp, bottom = 10.dp)
                )

                MoviesCarousel(
                    movies = sampleMovies,
                    onMovieClick = { movieId ->
                        navController.navigate("content/$movieId")
                    }
                )
            }

            // Continue Watching section
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Continue Watching",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start,
                    color = Color.White,
                    modifier = Modifier.padding(start = 25.dp, bottom = 10.dp)
                )

                ContinueMoviesCarousel(
                    movies = sampleMovies,
                    onMovieClick = { movieId ->
                        navController.navigate("content/$movieId")
                    }
                )
            }

            // Top TV Channels section
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Top Tv Channels",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start,
                    color = Color.White,
                    modifier = Modifier.padding(start = 25.dp, bottom = 10.dp)
                )

                TvChannelsCarousel(
                    movies = sampleMovies,
                    onMovieClick = { movieId ->
                        navController.navigate("content/$movieId")
                    }
                )
            }

            // Add some bottom padding
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        // Place NavigationTopBar last so it's always on top
        NavigationTopBar(
            navController = navController,
            onAvatarClick = {
                navController.navigate("profile") {
                    launchSingleTop = true
                    restoreState = true
                }
            },
        )
    }
}

@Composable
fun ContinueMoviesCarousel(
    movies: List<Movie>,
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
    movies: List<Movie>,
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
    movies: List<Movie>,
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
    item: Movie,
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