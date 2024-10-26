package com.easyjob.jetpack.data.store

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


// Set up DataStore instance
val Context.dataStore by preferencesDataStore(name = "user_prefs")

object UserPreferences {
    // Define keys for user info
    val JWT_KEY = stringPreferencesKey("jwt")
    val USER_ID_KEY = stringPreferencesKey("user_id")
    val NAME_KEY = stringPreferencesKey("name")
    val LAST_NAME_KEY = stringPreferencesKey("last_name")
    val ROLES_KEY = stringSetPreferencesKey("role")

}