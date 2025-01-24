package com.techker.tvvr.screens.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun NavigationTopBar(
    navController: NavController,
    onAvatarClick: () -> Unit = {}
) {
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
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(Icons.Default.Home, "Home", tint = Color.White)
            }
            IconButton(onClick = { navController.navigate("liveTv") }) {
                Icon(Icons.Default.LiveTv, "Live TV", tint = Color.White)
            }
            IconButton(onClick = { navController.navigate("dashboard") }) {
                Icon(Icons.Default.Dashboard, "Dashboard", tint = Color.White)
            }
        }

        // Profile button
        IconButton(
            onClick = onAvatarClick,
            modifier = Modifier.padding(end = 16.dp)
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
   // HomeScreen(navController = navController)
}