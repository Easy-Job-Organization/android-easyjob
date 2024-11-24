package com.easyjob.jetpack.data.store

import android.content.Context
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    // Save user info to DataStore
    suspend fun saveUserInfo(jwt: String, userId: String, name: String, lastName: String, roles: List<String>) {
        context.dataStore.edit { preferences ->
            preferences[UserPreferences.JWT_KEY] = jwt
            preferences[UserPreferences.USER_ID_KEY] = userId
            preferences[UserPreferences.NAME_KEY] = name
            preferences[UserPreferences.LAST_NAME_KEY] = lastName
            preferences[UserPreferences.ROLES_KEY] = roles.toSet()

        }
    }

    // Clear user info from DataStore
    suspend fun clearUserInfo() {
        context.dataStore.edit { preferences ->
            preferences.remove(UserPreferences.JWT_KEY)
            preferences.remove(UserPreferences.USER_ID_KEY)
            preferences.remove(UserPreferences.NAME_KEY)
            preferences.remove(UserPreferences.LAST_NAME_KEY)
            preferences.remove(UserPreferences.ROLES_KEY)
        }
    }


    // Retrieve JWT
    val jwtFlow: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[UserPreferences.JWT_KEY] }

    // Retrieve User ID
    val userIdFlow: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[UserPreferences.USER_ID_KEY] }

    // Retrieve Name
    val nameFlow: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[UserPreferences.NAME_KEY] }

    // Retrieve Last Name
    val lastNameFlow: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[UserPreferences.LAST_NAME_KEY] }

    // Retrieve Role
    val rolesFlow: Flow<List<String>> = context.dataStore.data
        .map { preferences ->
            preferences[UserPreferences.ROLES_KEY]?.toList() ?: emptyList()
        }

}
