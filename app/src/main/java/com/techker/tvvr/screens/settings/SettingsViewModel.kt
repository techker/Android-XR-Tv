package com.techker.tvvr.screens.settings

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    private val _option1 = MutableStateFlow(sharedPreferences.getBoolean("option1", false))
    val option1: StateFlow<Boolean> = _option1

    private val _option2 = MutableStateFlow(sharedPreferences.getBoolean("option2", true))
    val option2: StateFlow<Boolean> = _option2

    fun toggleOption1(enabled: Boolean) {
        sharedPreferences.edit().putBoolean("option1", enabled).apply()
        _option1.value = enabled
    }

    fun toggleOption2(enabled: Boolean) {
        sharedPreferences.edit().putBoolean("option2", enabled).apply()
        _option2.value = enabled
    }
}