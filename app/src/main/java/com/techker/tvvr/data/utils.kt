package com.techker.tvvr.data
import android.content.SharedPreferences
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun SharedPreferences.getBooleanFlow(key: String, defaultValue: Boolean): Flow<Boolean> {
    return callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            if (changedKey == key) {
                trySend(getBoolean(key, defaultValue))
            }
        }
        registerOnSharedPreferenceChangeListener(listener)
        // Emit the initial value
        trySend(getBoolean(key, defaultValue))
        awaitClose { unregisterOnSharedPreferenceChangeListener(listener) }
    }
}