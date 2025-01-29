package com.techker.tvvr.screens.settings

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techker.tvvr.screens.navigation.NavigationTopBar

@Composable
fun  SettingsScreen(
    navController: NavController,
) {
    // Access the current Context
    val context = LocalContext.current

    // Create or retrieve SharedPreferences
    val sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)

    // Create the ViewModel manually
    val viewModel = remember { SettingsViewModel(sharedPreferences) }

    // UI
    SettingsScreenContent(viewModel = viewModel)

    NavigationTopBar(
        navController = navController,
        onAvatarClick = {
            navController.navigate("settings") {
                launchSingleTop = true
                restoreState = true
            }
        },
    )

}

@Composable
fun SettingsScreenContent(viewModel: SettingsViewModel) {

    val option1 by viewModel.option1.collectAsState()
    val option2 by viewModel.option2.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Settings", style = MaterialTheme.typography.bodyMedium)

        SettingItem(
            title = "Epg with Top Program Info",
            isChecked = option1,
            onCheckedChange = { viewModel.toggleOption1(it) }
        )

        SettingItem(
            title = "Epg Pop Out Info Box",
            isChecked = option2,
            onCheckedChange = { viewModel.toggleOption2(it) }
        )
    }
}
@Composable
fun SettingItem(
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.bodySmall, color = Color.White)
        Switch(checked = isChecked, onCheckedChange = onCheckedChange)
    }
}

@Preview
@Composable
fun ShowSettingsScreenPrev(){
    val navController = rememberNavController() // Create a mock NavController
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        NavigationTopBar(
            navController = navController,
            onAvatarClick = {

            },
        )
        Text(text = "Settings", style = MaterialTheme.typography.bodyMedium, color = Color.White)

        SettingItem(
            title = "Epg with Top Program Info",
            isChecked = false,
            onCheckedChange = {  }
        )

        SettingItem(
            title = "Epg Pop Out Info Box",
            isChecked = true,
            onCheckedChange = {  }
        )
    }
}