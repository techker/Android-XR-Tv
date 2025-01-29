package com.techker.tvvr.screens.navigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.xr.compose.platform.LocalSession
import com.techker.tvvr.R


@Composable
fun NavigationTopBar(
    navController: NavController,
    onAvatarClick: () -> Unit = {}
) {
    val session = LocalSession.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Black.copy(alpha = 0.8f)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Navigation buttons
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp)
        ) {
            IconButton(
                onClick = {
                    Log.d("Navigation", "Home clicked")
                    try {
                        navController.navigate("home") {
                            launchSingleTop = true
                            popUpTo(navController.graph.startDestinationId)
                        }
                    } catch (e: Exception) {
                        Log.e("Navigation", "Failed to navigate to home", e)
                    }
                }
            ) {
                Icon(Icons.Default.Home, "Home", tint = Color.White)
            }
            IconButton(
                onClick = {
                    Log.d("Navigation", "EPG clicked")
                    try {
                        navController.navigate("liveTv")
                    } catch (e: Exception) {
                        Log.e("Navigation", "Failed to navigate to epg", e)
                    }
                }
            ) {
                Icon(Icons.Default.LiveTv, "Live TV", tint = Color.White)
            }
            IconButton(
                onClick = {
                    Log.d("Navigation", "Vod clicked")
                    try {
                        navController.navigate("vod")
                    } catch (e: Exception) {
                        Log.e("Navigation", "Failed to navigate to vod", e)
                    }
                }
            ) {
                Icon(Icons.Default.Movie, "VOD", tint = Color.White)
            }

            IconButton(
            onClick = { session?.requestFullSpaceMode() },
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_full_space_mode_switch),
                tint = Color.White,
                contentDescription = null
            )
        }

        }

        // Profile button
        IconButton(
            onClick = {
                Log.d("Navigation", "Profile clicked")
                onAvatarClick()
            },
            modifier = Modifier.padding(end = 26.dp)
        ) {
            Icon(Icons.Default.Person, "Profile", tint = Color.White)
        }

    }
}


@Preview(
    name = "Navigation Preview",
    showBackground = true,
    widthDp = 1280,
    heightDp = 720
)
@Composable
fun ShowNavPreview() {
    val navController = rememberNavController()
    NavigationTopBar(navController) {}
}