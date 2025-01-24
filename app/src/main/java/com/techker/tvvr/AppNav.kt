package com.techker.tvvr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.techker.tvvr.screens.epg.EPGTopInfoScreen
import com.techker.tvvr.screens.home.HomeScreen
import com.techker.tvvr.screens.player.PlayerScreen

@Composable
fun AppNav(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(
                navController = navController,
                modifier = Modifier.fillMaxSize()
            )
        }

        composable("profile") {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Text("Profile Screen", color = Color.White)
            }
        }

        composable("dashboard") {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Text("Dashboard Screen", color = Color.White)
            }
        }

        composable("settings") {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Text("Settings Screen", color = Color.White)
            }
        }

        composable("content/{contentId}") { backStackEntry ->
            val contentId = backStackEntry.arguments?.getString("contentId")
            // ContentScreen(navController, contentId)
        }

        composable("liveTv") {
            EPGTopInfoScreen(navController)
        }

        composable("player/{channelId}/{startTime}") { backStackEntry ->
            val channelId = backStackEntry.arguments?.getString("channelId")
            val startTime = backStackEntry.arguments?.getString("startTime")
            PlayerScreen(navController)
        }
    }
}