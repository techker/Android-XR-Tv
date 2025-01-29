package com.techker.tvvr.screens.onDemand

import android.content.Intent
import android.util.Log
import android.webkit.WebView
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.techker.tvvr.TvVrApplication
import com.techker.tvvr.data.Constants
import com.techker.tvvr.screens.navigation.NavigationTopBar
import com.techker.tvvr.viewmodel.OnDemandViewModel

@kotlin.OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieDetailsScreen(
    navController: NavController,
    movieId: Int,
    viewModel: OnDemandViewModel = viewModel(
        factory = OnDemandViewModelFactory((LocalContext.current.applicationContext as TvVrApplication).movieRepository)
    )
) {
    val context = LocalContext.current
    val movieDetails by viewModel.movieDetails.collectAsState()
    val trailers by viewModel.trailers.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Get the first official YouTube trailer
    val mainTrailer = trailers.firstOrNull { video ->
        video.site == "YouTube" && 
        video.type == "Trailer" && 
        video.official
    }

    Log.d("VOD", "Movie trailer key ${mainTrailer?.key}")
    
    // Only create ExoPlayer when we have a valid trailer key
    val exoPlayer = remember(mainTrailer?.key) {
        if (mainTrailer?.key != null) {
            ExoPlayer.Builder(context).build().apply {
                playWhenReady = true
                repeatMode = Player.REPEAT_MODE_OFF
            }
        } else null
    }

    // Clean up player when leaving screen or when key changes
    DisposableEffect(mainTrailer?.key) {
        onDispose {
            exoPlayer?.release()
        }
    }

    // Initial data fetch
    LaunchedEffect(movieId) {
        viewModel.fetchMovieDetails(movieId)
        viewModel.getSelectedTrailer(movieId)
    }

    Box(
        modifier = Modifier
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
                    .padding(16.dp)
            ) {
                // Hero Section with Backdrop
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        // Backdrop Image
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/original${movieDetails?.backdrop_path}",
                            contentDescription = movieDetails?.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        
                        // Gradient Overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.9f)
                                        )
                                    )
                                )
                        )
                    }
                }

                // Title and Release Year
                item {
                    Text(
                        text = movieDetails?.title ?: "",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = movieDetails?.release_date?.split("-")?.get(0) ?: "",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "•",
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "${movieDetails?.runtime ?: 0} min",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }

                // Trailer Section
                if (mainTrailer?.key != null) {
                    item {
                        Row{
                            Text(
                                text = "Trailer",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                            Icon(
                                imageVector = Icons.Filled.PlayCircle,
                                contentDescription = "Play trailer",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clickable{
                                        // Launch YouTube intent or in-app player
                                        val intent = Intent(Intent.ACTION_VIEW,
                                            (Constants.YOUTUBE_WATCH + mainTrailer.key).toUri())
                                        context.startActivity(intent)
                                    }
                            )
                        }
                    }
                }

                // Overview
                item {
                    Text(
                        text = "Overview",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                    )
                    Text(
                        text = movieDetails?.overview ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }

                // Genres
                item {
                    FlowRow(
                        modifier = Modifier.padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        movieDetails?.genres?.forEach { genre ->
                            Surface(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Text(
                                    text = genre.name,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
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
//Opens external Chrome..
//  YouTubePlayerScreen(
//  videoId = "https://www.youtube.com/watch?v=${mainTrailer.key}"
//  )

@Composable
fun YouTubePlayerScreen(videoId: String) {
    val context = LocalContext.current
    val webView = remember { WebView(context) }

    AndroidView(
        factory = {
            webView.apply {
                settings.javaScriptEnabled = true
                settings.mediaPlaybackRequiresUserGesture = false
                settings.domStorageEnabled = true

                loadUrl("https://www.youtube.com/embed/$videoId")
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}
//Fails to play youtube links
//  VideoPlayer(
//  videoUrl = "https://www.youtube.com/watch?v=${mainTrailer.key}",
//   modifier = Modifier.fillMaxSize()
//    )
// }
@OptIn(UnstableApi::class)
@Composable
private fun VideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(DefaultMediaSourceFactory(context))
            .build().apply {
                playWhenReady = true
                repeatMode = Player.REPEAT_MODE_OFF
                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        isLoading = playbackState == Player.STATE_BUFFERING
                    }
                })
            }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    Box(modifier = modifier) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true
                    setShowNextButton(false)
                    setShowPreviousButton(false)
                }
            },
            modifier = Modifier.fillMaxSize()
        ) { playerView ->
            try {
                val mediaItem = MediaItem.fromUri(videoUrl)
                exoPlayer.apply {
                    setMediaItem(mediaItem)
                    prepare()
                }
            } catch (e: Exception) {
                Log.e("VOD", "Error setting up video player", e)
            }
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(48.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}



