package com.easyjob.jetpack.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.models.Client
import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.repositories.ProfileRepository
import com.easyjob.jetpack.repositories.ProfileRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: ProfileRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val profile = MutableLiveData<Client>()
    val profileState = MutableLiveData<Int>() // 0: Idle, 1: Loading, 2: Error, 3: Success

    fun loadProfile() {

        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                profileState.value = 1 // Loading
            }

            val userId = userPreferencesRepository.userIdFlow.first()
            Log.e(">>>", "The user id is: ${userId}")

            val response = repo.fetchProfileClient(userId?: "")
            Log.e(">>>", "The response is: ${response}")
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    profile.value = response.body()
                    profileState.value = 3 // Success
                }
            } else {
                withContext(Dispatchers.Main) {
                    profileState.value = 2 // Error
                }
            }
        }
    }

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                userPreferencesRepository.clearUserInfo()
            }
        }

    }
}