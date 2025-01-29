package com.techker.tvvr

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.techker.tvvr.data.getBooleanFlow
import com.techker.tvvr.screens.epg.EPGScreen
import com.techker.tvvr.screens.epg.EPGTopInfoScreen
import com.techker.tvvr.screens.home.HomeScreen
import com.techker.tvvr.screens.onDemand.MovieDetailsScreen
import com.techker.tvvr.screens.onDemand.OnDemandScreen
import com.techker.tvvr.screens.player.PlayerScreen
import com.techker.tvvr.screens.settings.SettingsScreen

@Composable
fun AppNav(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    // retrieve SharedPreferences as Flow
    val sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
    val epgTopShow by sharedPreferences
        .getBooleanFlow("option1", false)
        .collectAsState(initial = false) // Provide an initial value
    Log.d("AppNav","Is epg top info enabled $epgTopShow")

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
        composable("vod") {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                OnDemandScreen(navController)
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
                SettingsScreen(navController)
            }
        }

        composable("content/{contentId}") { backStackEntry ->
            val contentId = backStackEntry.arguments?.getString("contentId")
            Log.d("NAV","Got movie ID $contentId")
            if(contentId?.isEmpty() == true){
                //Empty?
            }else{
                MovieDetailsScreen(navController,contentId!!.toInt())
            }

            // ContentScreen(navController, contentId)
        }

        composable("liveTv") {
            if (epgTopShow){
                EPGTopInfoScreen(navController)
            }else{
                EPGScreen(navController)
            }

        }

        composable("player/{channelId}/{startTime}") { backStackEntry ->
            val channelId = backStackEntry.arguments?.getString("channelId")
            val startTime = backStackEntry.arguments?.getString("startTime")
            PlayerScreen(navController, channelId.toString())
        }
    }
}